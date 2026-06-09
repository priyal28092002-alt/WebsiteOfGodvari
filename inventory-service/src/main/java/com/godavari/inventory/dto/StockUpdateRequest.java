package com.godavari.inventory.dto;

public class StockUpdateRequest {
    private Long    productId;
    private String  productName;
    private String  sku;
    private String  unit;
    private Integer quantity;
    private String  transactionType;
    private String  referenceId;
    private String  notes;
    private String  createdBy;
    private Integer lowStockThreshold;
    private String  warehouseLocation;

    public Long    getProductId()             { return productId; }
    public void    setProductId(Long p)       { this.productId = p; }
    public String  getProductName()           { return productName; }
    public void    setProductName(String n)   { this.productName = n; }
    public String  getSku()                   { return sku; }
    public void    setSku(String s)           { this.sku = s; }
    public String  getUnit()                  { return unit; }
    public void    setUnit(String u)          { this.unit = u; }
    public Integer getQuantity()              { return quantity; }
    public void    setQuantity(Integer q)     { this.quantity = q; }
    public String  getTransactionType()       { return transactionType; }
    public void    setTransactionType(String t){ this.transactionType = t; }
    public String  getReferenceId()           { return referenceId; }
    public void    setReferenceId(String r)   { this.referenceId = r; }
    public String  getNotes()                 { return notes; }
    public void    setNotes(String n)         { this.notes = n; }
    public String  getCreatedBy()             { return createdBy; }
    public void    setCreatedBy(String c)     { this.createdBy = c; }
    public Integer getLowStockThreshold()     { return lowStockThreshold; }
    public void    setLowStockThreshold(Integer t) { this.lowStockThreshold = t; }
    public String  getWarehouseLocation()     { return warehouseLocation; }
    public void    setWarehouseLocation(String l) { this.warehouseLocation = l; }
}
