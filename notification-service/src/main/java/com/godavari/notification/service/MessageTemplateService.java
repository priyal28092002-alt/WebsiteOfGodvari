package com.godavari.notification.service;

import com.godavari.notification.dto.InvoicePaidEvent;
import com.godavari.notification.dto.OrderPlacedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class MessageTemplateService {

    @Value("${whatsapp.company-name:Godavari Building Materials}")
    private String companyName;

    @Value("${whatsapp.company-city:Nashik, Maharashtra}")
    private String companyCity;

    @Value("${whatsapp.company-phone:+91-XXXXX-XXXXX}")
    private String companyPhone;

    private final NumberFormat currencyFmt = NumberFormat.getInstance(new Locale("en", "IN"));

    /** ─── ORDER PLACED MESSAGE ─── */
    public String buildOrderPlacedMessage(OrderPlacedEvent event) {
        StringBuilder sb = new StringBuilder();

        sb.append("🏗️ *").append(companyName).append("*\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("✅ *ORDER CONFIRMED!*\n\n");

        sb.append("Hello *").append(event.getCustomerName()).append("*,\n");
        sb.append("Thank you for your order! Here are your order details:\n\n");

        sb.append("📋 *Order Number:* ").append(event.getOrderNumber()).append("\n");
        sb.append("📅 *Expected Delivery:* ").append(event.getExpectedDeliveryDate()).append("\n");
        sb.append("💳 *Payment Method:* ").append(formatPaymentMethod(event.getPaymentMethod())).append("\n\n");

        // Items
        sb.append("🛒 *Items Ordered:*\n");
        if (event.getItems() != null) {
            for (int i = 0; i < event.getItems().size(); i++) {
                OrderPlacedEvent.OrderItem item = event.getItems().get(i);
                sb.append("  ").append(i + 1).append(". *").append(item.getProductName()).append("*\n");
                sb.append("     Qty: ").append(item.getQuantity()).append(" ").append(item.getUnit());
                sb.append(" × ₹").append(fmt(item.getUnitPrice()));
                sb.append(" = *₹").append(fmt(item.getTotalPrice())).append("*\n");
            }
        }
        sb.append("\n");

        // Price breakdown
        sb.append("💰 *Price Breakdown:*\n");
        sb.append("  Subtotal:  ₹").append(fmt(event.getSubtotal())).append("\n");
        sb.append("  GST:       ₹").append(fmt(event.getGstAmount())).append("\n");
        if (event.getDeliveryCharge() != null && event.getDeliveryCharge().compareTo(BigDecimal.ZERO) > 0) {
            sb.append("  Delivery:  ₹").append(fmt(event.getDeliveryCharge())).append("\n");
        } else {
            sb.append("  Delivery:  🎉 FREE\n");
        }
        sb.append("  ─────────────────\n");
        sb.append("  *TOTAL:    ₹").append(fmt(event.getTotalAmount())).append("*\n\n");

        // Delivery address
        sb.append("📍 *Delivery Address:*\n");
        sb.append("  ").append(event.getDeliveryAddress()).append(", ").append(event.getDeliveryCity()).append("\n\n");

        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("For any queries, call us:\n");
        sb.append("📞 ").append(companyPhone).append("\n");
        sb.append("📍 ").append(companyCity).append("\n\n");
        sb.append("_Thank you for choosing ").append(companyName).append("!_ 🙏");

        return sb.toString();
    }

    /** ─── INVOICE PAID MESSAGE ─── */
    public String buildInvoicePaidMessage(InvoicePaidEvent event) {
        StringBuilder sb = new StringBuilder();

        sb.append("🏗️ *").append(companyName).append("*\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("✅ *PAYMENT RECEIVED!*\n\n");

        sb.append("Hello *").append(event.getCustomerName()).append("*,\n");
        sb.append("Your payment has been successfully received. Here is your payment receipt:\n\n");

        // Invoice details
        sb.append("🧾 *Invoice Details:*\n");
        sb.append("  Invoice No: *").append(event.getInvoiceNumber()).append("*\n");
        sb.append("  Order No:   *").append(event.getOrderNumber()).append("*\n");
        sb.append("  Paid On:    ").append(formatDate(event.getPaidAt())).append("\n");
        sb.append("  Method:     ").append(formatPaymentMethod(event.getPaymentMethod())).append("\n\n");

        // GST breakdown
        sb.append("💰 *Payment Breakdown:*\n");
        sb.append("  Subtotal:      ₹").append(fmt(event.getSubtotal())).append("\n");

        if (event.getIgst() != null && event.getIgst().compareTo(BigDecimal.ZERO) > 0) {
            sb.append("  IGST:          ₹").append(fmt(event.getIgst())).append("\n");
        } else {
            BigDecimal cgst = event.getCgst() != null ? event.getCgst() : BigDecimal.ZERO;
            BigDecimal sgst = event.getSgst() != null ? event.getSgst() : BigDecimal.ZERO;
            sb.append("  CGST:          ₹").append(fmt(cgst)).append("\n");
            sb.append("  SGST:          ₹").append(fmt(sgst)).append("\n");
        }
        sb.append("  Total GST:     ₹").append(fmt(event.getTotalGst())).append("\n");
        sb.append("  ─────────────────────\n");
        sb.append("  *AMOUNT PAID:  ₹").append(fmt(event.getTotalAmount())).append("*\n\n");

        // Billing address
        if (event.getBillingAddress() != null && !event.getBillingAddress().isBlank()) {
            sb.append("📍 *Billed To:*\n");
            sb.append("  ").append(event.getBillingAddress()).append("\n\n");
        }

        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("🔒 *This is an official tax invoice.*\n");
        sb.append("Keep this message for your records.\n\n");
        sb.append("For queries: 📞 ").append(companyPhone).append("\n");
        sb.append("📍 ").append(companyCity).append("\n\n");
        sb.append("_Thank you for your business!_ 🙏");

        return sb.toString();
    }

    private String fmt(BigDecimal amount) {
        if (amount == null) return "0.00";
        return String.format("%,.2f", amount);
    }

    private String formatPaymentMethod(String method) {
        if (method == null) return "—";
        return switch (method.toUpperCase()) {
            case "CASH"          -> "💵 Cash on Delivery";
            case "UPI"           -> "📱 UPI";
            case "BANK_TRANSFER" -> "🏦 Bank Transfer";
            case "CHEQUE"        -> "📝 Cheque";
            default -> method;
        };
    }

    private String formatDate(String dateTime) {
        if (dateTime == null) return "—";
        try { return dateTime.substring(0, 10); } catch (Exception e) { return dateTime; }
    }
}
