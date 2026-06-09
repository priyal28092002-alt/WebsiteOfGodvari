package com.godavari.notification.dto;

import java.math.BigDecimal;

public class InvoicePaidEvent {
    private Long invoiceId;
    private String invoiceNumber;
    private Long orderId;
    private String orderNumber;
    private Long userId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal subtotal;
    private BigDecimal cgst;
    private BigDecimal sgst;
    private BigDecimal igst;
    private BigDecimal totalGst;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String paidAt;
    private String billingAddress;

    public Long getInvoiceId()           { return invoiceId; }
    public void setInvoiceId(Long id)    { this.invoiceId = id; }
    public String getInvoiceNumber()     { return invoiceNumber; }
    public void setInvoiceNumber(String n){ this.invoiceNumber = n; }
    public Long getOrderId()             { return orderId; }
    public void setOrderId(Long id)      { this.orderId = id; }
    public String getOrderNumber()       { return orderNumber; }
    public void setOrderNumber(String n) { this.orderNumber = n; }
    public Long getUserId()              { return userId; }
    public void setUserId(Long id)       { this.userId = id; }
    public String getCustomerName()      { return customerName; }
    public void setCustomerName(String n){ this.customerName = n; }
    public String getCustomerEmail()     { return customerEmail; }
    public void setCustomerEmail(String e){ this.customerEmail = e; }
    public String getCustomerPhone()     { return customerPhone; }
    public void setCustomerPhone(String p){ this.customerPhone = p; }
    public BigDecimal getSubtotal()      { return subtotal; }
    public void setSubtotal(BigDecimal s){ this.subtotal = s; }
    public BigDecimal getCgst()          { return cgst; }
    public void setCgst(BigDecimal c)    { this.cgst = c; }
    public BigDecimal getSgst()          { return sgst; }
    public void setSgst(BigDecimal s)    { this.sgst = s; }
    public BigDecimal getIgst()          { return igst; }
    public void setIgst(BigDecimal i)    { this.igst = i; }
    public BigDecimal getTotalGst()      { return totalGst; }
    public void setTotalGst(BigDecimal g){ this.totalGst = g; }
    public BigDecimal getTotalAmount()   { return totalAmount; }
    public void setTotalAmount(BigDecimal t){ this.totalAmount = t; }
    public String getPaymentMethod()     { return paymentMethod; }
    public void setPaymentMethod(String p){ this.paymentMethod = p; }
    public String getPaidAt()            { return paidAt; }
    public void setPaidAt(String p)      { this.paidAt = p; }
    public String getBillingAddress()    { return billingAddress; }
    public void setBillingAddress(String a){ this.billingAddress = a; }
}
