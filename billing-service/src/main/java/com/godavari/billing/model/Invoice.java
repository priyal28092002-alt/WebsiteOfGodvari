package com.godavari.billing.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    private Long orderId;
    private String orderNumber;   // added for frontend display
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userGst;

    @Column(columnDefinition = "TEXT")
    private String billingAddress;

    private BigDecimal subtotal   = BigDecimal.ZERO;
    private BigDecimal cgst       = BigDecimal.ZERO;
    private BigDecimal sgst       = BigDecimal.ZERO;
    private BigDecimal igst       = BigDecimal.ZERO;
    private BigDecimal totalGst   = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private String paymentMethod;
    private LocalDate invoiceDate  = LocalDate.now();
    private LocalDate dueDate;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum PaymentStatus { PENDING, PAID, OVERDUE }

    // ── Frontend-friendly computed getters ──
    public String getCustomerName()  { return userName; }
    public String getCustomerEmail() { return userEmail; }
    public String getCustomerPhone() { return userPhone; }
    public String getGstNumber()     { return userGst; }
    public boolean isPaid()          { return paymentStatus == PaymentStatus.PAID; }
    public BigDecimal getGstAmount() { return totalGst; }
    public BigDecimal getCgstAmount(){ return cgst; }
    public BigDecimal getSgstAmount(){ return sgst; }
    public BigDecimal getIgstAmount(){ return igst; }

    // ── Getters & Setters ──
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }
    public String getUserGst() { return userGst; }
    public void setUserGst(String userGst) { this.userGst = userGst; }
    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getCgst() { return cgst; }
    public void setCgst(BigDecimal cgst) { this.cgst = cgst; }
    public BigDecimal getSgst() { return sgst; }
    public void setSgst(BigDecimal sgst) { this.sgst = sgst; }
    public BigDecimal getIgst() { return igst; }
    public void setIgst(BigDecimal igst) { this.igst = igst; }
    public BigDecimal getTotalGst() { return totalGst; }
    public void setTotalGst(BigDecimal totalGst) { this.totalGst = totalGst; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
