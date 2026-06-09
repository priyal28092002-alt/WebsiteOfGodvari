package com.godavari.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long productId;

    private String productName;
    private String sku;
    private String unit;
    private Integer quantityAvailable = 0;
    private Integer quantityReserved  = 0;
    private Integer lowStockThreshold = 50;
    private String  warehouseLocation;
    private LocalDateTime lastRestockedAt;
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() { this.updatedAt = LocalDateTime.now(); }

    public Long    getId()                    { return id; }
    public void    setId(Long id)             { this.id = id; }
    public Long    getProductId()             { return productId; }
    public void    setProductId(Long p)       { this.productId = p; }
    public String  getProductName()           { return productName; }
    public void    setProductName(String n)   { this.productName = n; }
    public String  getSku()                   { return sku; }
    public void    setSku(String s)           { this.sku = s; }
    public String  getUnit()                  { return unit; }
    public void    setUnit(String u)          { this.unit = u; }
    public Integer getQuantityAvailable()     { return quantityAvailable; }
    public void    setQuantityAvailable(Integer q) { this.quantityAvailable = q; }
    public Integer getQuantityReserved()      { return quantityReserved; }
    public void    setQuantityReserved(Integer q)  { this.quantityReserved = q; }
    public Integer getLowStockThreshold()     { return lowStockThreshold; }
    public void    setLowStockThreshold(Integer t) { this.lowStockThreshold = t; }
    public String  getWarehouseLocation()     { return warehouseLocation; }
    public void    setWarehouseLocation(String l) { this.warehouseLocation = l; }
    public LocalDateTime getLastRestockedAt() { return lastRestockedAt; }
    public void    setLastRestockedAt(LocalDateTime t) { this.lastRestockedAt = t; }
    public LocalDateTime getUpdatedAt()       { return updatedAt; }
    public boolean isLowStock()               { return quantityAvailable <= lowStockThreshold; }
}
