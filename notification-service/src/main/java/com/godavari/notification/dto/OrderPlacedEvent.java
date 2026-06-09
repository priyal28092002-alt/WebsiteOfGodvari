package com.godavari.notification.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderPlacedEvent {
    private Long orderId;
    private String orderNumber;
    private Long userId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String deliveryAddress;
    private String deliveryCity;
    private String paymentMethod;
    private BigDecimal totalAmount;
    private BigDecimal subtotal;
    private BigDecimal gstAmount;
    private BigDecimal deliveryCharge;
    private String expectedDeliveryDate;
    private List<OrderItem> items;

    public static class OrderItem {
        private String productName;
        private String sku;
        private Integer quantity;
        private String unit;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private BigDecimal gstRate;

        public String getProductName()  { return productName; }
        public void setProductName(String p) { this.productName = p; }
        public String getSku()          { return sku; }
        public void setSku(String s)    { this.sku = s; }
        public Integer getQuantity()    { return quantity; }
        public void setQuantity(Integer q) { this.quantity = q; }
        public String getUnit()         { return unit; }
        public void setUnit(String u)   { this.unit = u; }
        public BigDecimal getUnitPrice()  { return unitPrice; }
        public void setUnitPrice(BigDecimal p) { this.unitPrice = p; }
        public BigDecimal getTotalPrice() { return totalPrice; }
        public void setTotalPrice(BigDecimal p) { this.totalPrice = p; }
        public BigDecimal getGstRate()  { return gstRate; }
        public void setGstRate(BigDecimal g) { this.gstRate = g; }
    }

    public Long getOrderId()              { return orderId; }
    public void setOrderId(Long id)       { this.orderId = id; }
    public String getOrderNumber()        { return orderNumber; }
    public void setOrderNumber(String n)  { this.orderNumber = n; }
    public Long getUserId()               { return userId; }
    public void setUserId(Long id)        { this.userId = id; }
    public String getCustomerName()       { return customerName; }
    public void setCustomerName(String n) { this.customerName = n; }
    public String getCustomerEmail()      { return customerEmail; }
    public void setCustomerEmail(String e){ this.customerEmail = e; }
    public String getCustomerPhone()      { return customerPhone; }
    public void setCustomerPhone(String p){ this.customerPhone = p; }
    public String getDeliveryAddress()    { return deliveryAddress; }
    public void setDeliveryAddress(String a){ this.deliveryAddress = a; }
    public String getDeliveryCity()       { return deliveryCity; }
    public void setDeliveryCity(String c) { this.deliveryCity = c; }
    public String getPaymentMethod()      { return paymentMethod; }
    public void setPaymentMethod(String p){ this.paymentMethod = p; }
    public BigDecimal getTotalAmount()    { return totalAmount; }
    public void setTotalAmount(BigDecimal t){ this.totalAmount = t; }
    public BigDecimal getSubtotal()       { return subtotal; }
    public void setSubtotal(BigDecimal s) { this.subtotal = s; }
    public BigDecimal getGstAmount()      { return gstAmount; }
    public void setGstAmount(BigDecimal g){ this.gstAmount = g; }
    public BigDecimal getDeliveryCharge() { return deliveryCharge; }
    public void setDeliveryCharge(BigDecimal d){ this.deliveryCharge = d; }
    public String getExpectedDeliveryDate(){ return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(String d){ this.expectedDeliveryDate = d; }
    public List<OrderItem> getItems()     { return items; }
    public void setItems(List<OrderItem> i){ this.items = i; }
}
