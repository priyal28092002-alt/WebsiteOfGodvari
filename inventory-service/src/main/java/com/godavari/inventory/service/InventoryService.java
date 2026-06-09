package com.godavari.inventory.service;

import com.godavari.inventory.dto.StockUpdateRequest;
import com.godavari.inventory.model.Inventory;
import com.godavari.inventory.model.StockTransaction;
import com.godavari.inventory.repository.InventoryRepository;
import com.godavari.inventory.repository.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {

    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private StockTransactionRepository transactionRepository;

    @Cacheable(value = "inventory", key = "#productId")
    public Inventory getByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
    }

    public List<Inventory> getAll() { return inventoryRepository.findAll(); }

    public List<Inventory> getLowStockAlerts() { return inventoryRepository.findLowStockItems(); }

    @Transactional
    @CacheEvict(value = "inventory", key = "#req.productId")
    public Inventory updateStock(StockUpdateRequest req) {
        Inventory inventory = inventoryRepository.findByProductId(req.getProductId())
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setProductId(req.getProductId());
                    inv.setProductName(req.getProductName());
                    if (req.getSku()  != null) inv.setSku(req.getSku());
                    if (req.getUnit() != null) inv.setUnit(req.getUnit());
                    return inv;
                });

        // Update unit/sku if provided
        if (req.getSku()  != null) inventory.setSku(req.getSku());
        if (req.getUnit() != null) inventory.setUnit(req.getUnit());

        StockTransaction.TransactionType type =
                StockTransaction.TransactionType.valueOf(req.getTransactionType().toUpperCase());

        switch (type) {
            case IN -> {
                inventory.setQuantityAvailable(inventory.getQuantityAvailable() + req.getQuantity());
                inventory.setLastRestockedAt(LocalDateTime.now());
            }
            case OUT -> {
                if (inventory.getQuantityAvailable() < req.getQuantity())
                    throw new IllegalArgumentException("Insufficient stock");
                inventory.setQuantityAvailable(inventory.getQuantityAvailable() - req.getQuantity());
            }
            case ADJUSTMENT -> inventory.setQuantityAvailable(req.getQuantity());
        }

        if (req.getLowStockThreshold() != null) inventory.setLowStockThreshold(req.getLowStockThreshold());
        if (req.getWarehouseLocation() != null) inventory.setWarehouseLocation(req.getWarehouseLocation());

        StockTransaction txn = new StockTransaction();
        txn.setProductId(req.getProductId());
        txn.setTransactionType(type);
        txn.setQuantity(req.getQuantity());
        txn.setReferenceId(req.getReferenceId());
        txn.setNotes(req.getNotes());
        txn.setCreatedBy(req.getCreatedBy());
        transactionRepository.save(txn);

        return inventoryRepository.save(inventory);
    }

    public List<StockTransaction> getTransactions(Long productId) {
        return transactionRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
}
