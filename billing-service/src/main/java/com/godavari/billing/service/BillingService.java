package com.godavari.billing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.godavari.billing.dto.InvoiceRequest;
import com.godavari.billing.model.Invoice;
import com.godavari.billing.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BillingService {

    private static final Logger log = LoggerFactory.getLogger(BillingService.class);

    @Autowired private InvoiceRepository invoiceRepository;
    @Autowired(required = false) private KafkaTemplate<String, String> kafkaTemplate;

    private static final String INVOICE_PAID_TOPIC = "invoice.paid";
    private static final String ORDER_SERVICE      = "http://localhost:8087";
    private static final String WHATSAPP_URL       = "http://localhost:9091/send";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper  = new ObjectMapper().registerModule(new JavaTimeModule());

    public Invoice generateInvoice(InvoiceRequest req) {
        if (invoiceRepository.findByOrderId(req.getOrderId()).isPresent()) {
            log.warn("==> Invoice already exists for orderId={}", req.getOrderId());
            throw new IllegalArgumentException("Invoice already exists for order: " + req.getOrderId());
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        invoice.setOrderId(req.getOrderId());
        invoice.setOrderNumber(req.getOrderNumber());
        invoice.setUserId(req.getUserId());
        invoice.setUserName(req.getUserName());
        invoice.setUserEmail(req.getUserEmail());
        invoice.setUserPhone(req.getUserPhone());
        invoice.setUserGst(req.getUserGst());
        invoice.setBillingAddress(req.getBillingAddress());
        invoice.setSubtotal(req.getSubtotal() != null ? req.getSubtotal() : BigDecimal.ZERO);
        invoice.setTotalAmount(req.getTotalAmount() != null ? req.getTotalAmount() : BigDecimal.ZERO);
        invoice.setPaymentMethod(req.getPaymentMethod());
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(15));

        BigDecimal gst = req.getGstAmount() != null ? req.getGstAmount() : BigDecimal.ZERO;
        if (req.isInterState()) {
            invoice.setIgst(gst);
        } else {
            BigDecimal half = gst.divide(BigDecimal.valueOf(2));
            invoice.setCgst(half);
            invoice.setSgst(half);
        }
        invoice.setTotalGst(gst);

        Invoice saved = invoiceRepository.save(invoice);
        log.info("==> Invoice created: {} for orderId={}", saved.getInvoiceNumber(), req.getOrderId());
        return saved;
    }

    public Invoice getById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found: " + id));
    }

    public Invoice getByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Invoice not found for order: " + orderId));
    }

    public List<Invoice> getByUserId(Long userId) {
        return invoiceRepository.findByUserId(userId);
    }

    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    public Invoice markAsPaid(Long id) {
        Invoice invoice = getById(id);
        invoice.setPaymentStatus(Invoice.PaymentStatus.PAID);
        invoice.setPaidAt(LocalDateTime.now());
        Invoice saved = invoiceRepository.save(invoice);
        log.info("==> Invoice {} marked as PAID for orderId={}", saved.getInvoiceNumber(), saved.getOrderId());

        // 1. Update order payment status
        if (saved.getOrderId() != null) {
            try {
                String url = ORDER_SERVICE + "/orders/" + saved.getOrderId() + "/payment-status?paymentStatus=PAID";
                restTemplate.put(url, null);
                log.info("==> Order {} payment status updated to PAID", saved.getOrderId());
            } catch (Exception e) {
                log.warn("==> Could not update order payment status for orderId={}: {}", saved.getOrderId(), e.getMessage());
            }
        }

        // 2. Send notification — Kafka if available, direct WhatsApp otherwise
        notifyInvoicePaid(saved);

        return saved;
    }

    private void notifyInvoicePaid(Invoice invoice) {
        if (kafkaTemplate != null) {
            publishInvoiceKafka(invoice);
        } else {
            log.warn("==> Kafka not available, sending WhatsApp directly");
            sendInvoiceWhatsApp(invoice);
        }
    }

    private void publishInvoiceKafka(Invoice invoice) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("invoiceId",     invoice.getId());
            event.put("invoiceNumber", invoice.getInvoiceNumber());
            event.put("orderId",       invoice.getOrderId());
            event.put("orderNumber",   invoice.getOrderNumber());
            event.put("customerName",  invoice.getUserName());
            event.put("customerEmail", invoice.getUserEmail());
            event.put("customerPhone", invoice.getUserPhone());
            event.put("subtotal",      invoice.getSubtotal());
            event.put("cgst",          invoice.getCgst());
            event.put("sgst",          invoice.getSgst());
            event.put("igst",          invoice.getIgst());
            event.put("totalGst",      invoice.getTotalGst());
            event.put("totalAmount",   invoice.getTotalAmount());
            event.put("paymentMethod", invoice.getPaymentMethod());
            event.put("paidAt",        invoice.getPaidAt() != null ? invoice.getPaidAt().toString() : "");
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(INVOICE_PAID_TOPIC, invoice.getInvoiceNumber(), json);
            log.info("==> KAFKA PUBLISHED | topic={} invoiceNumber={}", INVOICE_PAID_TOPIC, invoice.getInvoiceNumber());
        } catch (Exception e) {
            log.warn("==> KAFKA PUBLISH FAILED | {} — falling back to direct WhatsApp: {}", invoice.getInvoiceNumber(), e.getMessage());
            sendInvoiceWhatsApp(invoice);
        }
    }

    private void sendInvoiceWhatsApp(Invoice invoice) {
        String phone = invoice.getUserPhone();
        if (phone == null || phone.isBlank()) {
            log.warn("==> WHATSAPP SKIP | No phone for invoice {}", invoice.getInvoiceNumber());
            return;
        }
        try {
            StringBuilder msg = new StringBuilder();
            msg.append("✅ *Payment Received!*\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("🧾 Invoice: *").append(invoice.getInvoiceNumber()).append("*\n");
            msg.append("📦 Order: ").append(invoice.getOrderNumber()).append("\n");
            msg.append("👤 Name: ").append(invoice.getUserName()).append("\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("💰 Subtotal: ₹").append(invoice.getSubtotal()).append("\n");
            if (invoice.getCgst() != null && invoice.getCgst().compareTo(BigDecimal.ZERO) > 0) {
                msg.append("📊 CGST: ₹").append(invoice.getCgst()).append("\n");
                msg.append("📊 SGST: ₹").append(invoice.getSgst()).append("\n");
            }
            if (invoice.getIgst() != null && invoice.getIgst().compareTo(BigDecimal.ZERO) > 0) {
                msg.append("📊 IGST: ₹").append(invoice.getIgst()).append("\n");
            }
            msg.append("💵 *Total Paid: ₹").append(invoice.getTotalAmount()).append("*\n");
            msg.append("💳 Payment: ").append(invoice.getPaymentMethod()).append("\n");
            msg.append("📅 Paid At: ").append(invoice.getPaidAt() != null
                    ? invoice.getPaidAt().toString().replace("T", " ").substring(0, 16) : "Now").append("\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("Thank you! - Godavari Building Materials, Nashik");

            sendWhatsApp(phone, msg.toString(), invoice.getInvoiceNumber());
        } catch (Exception e) {
            log.warn("==> WHATSAPP INVOICE MSG BUILD FAILED | {}: {}", invoice.getInvoiceNumber(), e.getMessage());
        }
    }

    private void sendWhatsApp(String phone, String message, String ref) {
        try {
            log.info("==> =============================================");
            log.info("==> WHATSAPP SEND | to={} | ref={}", phone, ref);
            log.info("==> =============================================");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = new HashMap<>();
            body.put("phone", phone);
            body.put("message", message);
            String json = objectMapper.writeValueAsString(body);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(WHATSAPP_URL, entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("==> ✅ WHATSAPP SENT | to={} | ref={}", phone, ref);
            } else {
                log.error("==> ❌ WHATSAPP FAILED | to={} | status={}", phone, response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("==> ❌ WHATSAPP ERROR | to={} | Is whatsapp-server running on port 9091? | error={}", phone, e.getMessage());
        }
    }
}
