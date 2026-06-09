package com.godavari.inventory.controller;

import com.godavari.inventory.dto.StockUpdateRequest;
import com.godavari.inventory.model.Inventory;
import com.godavari.inventory.model.StockTransaction;
import com.godavari.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    @Autowired private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAll() {
        log.info("==> GET ALL INVENTORY");
        List<Inventory> list = inventoryService.getAll();
        log.info("==> GET ALL INVENTORY SUCCESS | count={}", list.size());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getByProduct(@PathVariable Long productId) {
        log.info("==> GET INVENTORY | productId={}", productId);
        return ResponseEntity.ok(inventoryService.getByProductId(productId));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory>> getLowStock() {
        log.info("==> GET LOW STOCK ALERTS");
        List<Inventory> list = inventoryService.getLowStockAlerts();
        log.info("==> GET LOW STOCK SUCCESS | lowStockCount={}", list.size());
        return ResponseEntity.ok(list);
    }

    @PostMapping("/update")
    public ResponseEntity<Inventory> updateStock(@RequestBody StockUpdateRequest req) {
        log.info("==> UPDATE STOCK | productId={} type={} qty={}",
                req.getProductId(), req.getTransactionType(), req.getQuantity());
        try {
            Inventory inv = inventoryService.updateStock(req);
            log.info("==> UPDATE STOCK SUCCESS | productId={} newQty={}", req.getProductId(), inv.getQuantityAvailable());
            return ResponseEntity.ok(inv);
        } catch (Exception e) {
            log.error("==> UPDATE STOCK FAILED | productId={}: {}", req.getProductId(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/transactions/{productId}")
    public ResponseEntity<List<StockTransaction>> getTransactions(@PathVariable Long productId) {
        log.info("==> GET STOCK TRANSACTIONS | productId={}", productId);
        return ResponseEntity.ok(inventoryService.getTransactions(productId));
    }
}
