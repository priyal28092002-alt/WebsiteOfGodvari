package com.godavari.quotation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.godavari.quotation.model.Quotation;
import com.godavari.quotation.model.QuotationItem;
import com.godavari.quotation.repository.QuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class QuotationService {

    private static final Logger log = LoggerFactory.getLogger(QuotationService.class);

    @Autowired private QuotationRepository quotationRepository;
    @Autowired(required = false) private KafkaTemplate<String, String> kafkaTemplate;

    private static final String ORDERS_URL    = "http://localhost:8087/orders";
    private static final String WHATSAPP_URL  = "http://localhost:9091/send";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper  = new ObjectMapper().registerModule(new JavaTimeModule());

    public Quotation create(Quotation quotation) {
        quotation.setQuotationNumber("RFQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        quotation.setStatus(Quotation.QuotationStatus.SUBMITTED);
        quotation.setValidUntil(LocalDate.now().plusDays(30));
        for (QuotationItem item : quotation.getItems()) item.setQuotation(quotation);
        Quotation saved = quotationRepository.save(quotation);
        log.info("==> QUOTATION CREATED | number={} userId={} phone={}",
                saved.getQuotationNumber(), saved.getUserId(), saved.getUserPhone());
        return saved;
    }

    public Quotation getById(Long id) {
        return quotationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quotation not found: " + id));
    }

    public Page<Quotation> getByUser(Long userId, int page, int size) {
        return quotationRepository.findByUserId(userId,
                PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<Quotation> getAll(int page, int size) {
        return quotationRepository.findAll(
                PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Quotation updateStatus(Long id, String status, String adminRemarks) {
        Quotation q = getById(id);
        Quotation.QuotationStatus newStatus = Quotation.QuotationStatus.valueOf(status.toUpperCase());
        q.setStatus(newStatus);
        if (adminRemarks != null) q.setAdminRemarks(adminRemarks);
        Quotation saved = quotationRepository.save(q);

        log.info("==> QUOTATION STATUS UPDATED | number={} status={} phone={}",
                saved.getQuotationNumber(), newStatus, saved.getUserPhone());

        if (newStatus == Quotation.QuotationStatus.APPROVED) {
            createOrderFromQuotation(saved);
            sendWhatsApp(saved, "APPROVED");
        } else if (newStatus == Quotation.QuotationStatus.REJECTED) {
            sendWhatsApp(saved, "REJECTED");
        }

        return saved;
    }

    private void sendWhatsApp(Quotation q, String status) {
        String phone = q.getUserPhone();
        if (phone == null || phone.isBlank()) {
            log.warn("==> WHATSAPP SKIP | No phone for quotation {}", q.getQuotationNumber());
            return;
        }

        String message = buildMessage(q, status);

        // Kafka if available, direct HTTP otherwise
        if (kafkaTemplate != null) {
            publishKafka(q, status, message);
        } else {
            sendDirect(phone, message, q.getQuotationNumber());
        }
    }

    private String buildMessage(Quotation q, String status) {
        StringBuilder msg = new StringBuilder();
        if ("APPROVED".equals(status)) {
            msg.append("✅ *Quotation Approved!*\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("📋 RFQ: *").append(q.getQuotationNumber()).append("*\n");
            msg.append("👤 Name: ").append(q.getUserName()).append("\n");
            if (q.getCompanyName() != null && !q.getCompanyName().isBlank())
                msg.append("🏢 Company: ").append(q.getCompanyName()).append("\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("📦 Items:\n");
            for (QuotationItem item : q.getItems()) {
                msg.append("  • ").append(item.getProductName())
                   .append(" × ").append(item.getQuantity())
                   .append(" ").append(item.getUnit() != null ? item.getUnit() : "").append("\n");
            }
            if (q.getTotalAmount() != null)
                msg.append("💰 *Total: ₹").append(q.getTotalAmount()).append("*\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            if (q.getAdminRemarks() != null && !q.getAdminRemarks().isBlank())
                msg.append("💬 Remarks: ").append(q.getAdminRemarks()).append("\n");
            msg.append("🎉 Your order has been created automatically.\n");
            msg.append("Please check your orders page.\n\n");
        } else {
            msg.append("❌ *Quotation Rejected*\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("📋 RFQ: *").append(q.getQuotationNumber()).append("*\n");
            msg.append("👤 Name: ").append(q.getUserName()).append("\n");
            if (q.getCompanyName() != null && !q.getCompanyName().isBlank())
                msg.append("🏢 Company: ").append(q.getCompanyName()).append("\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            if (q.getAdminRemarks() != null && !q.getAdminRemarks().isBlank())
                msg.append("💬 Reason: ").append(q.getAdminRemarks()).append("\n");
            else
                msg.append("💬 Reason: Not specified\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("Please contact us for more details or submit a new RFQ.\n\n");
        }
        msg.append("Godavari Building Materials, Nashik");
        return msg.toString();
    }

    private void publishKafka(Quotation q, String status, String message) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("type",             "quotation." + status.toLowerCase());
            event.put("quotationNumber",  q.getQuotationNumber());
            event.put("customerName",     q.getUserName());
            event.put("customerPhone",    q.getUserPhone());
            event.put("status",           status);
            event.put("adminRemarks",     q.getAdminRemarks());
            event.put("message",          message);
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("quotation.status", q.getQuotationNumber(), json);
            log.info("==> KAFKA PUBLISHED | topic=quotation.status number={} status={}",
                    q.getQuotationNumber(), status);
        } catch (Exception e) {
            log.warn("==> KAFKA PUBLISH FAILED | {} — falling back to direct WhatsApp: {}",
                    q.getQuotationNumber(), e.getMessage());
            sendDirect(q.getUserPhone(), message, q.getQuotationNumber());
        }
    }

    private void sendDirect(String phone, String message, String ref) {
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
            log.error("==> ❌ WHATSAPP ERROR | to={} | Is whatsapp-server on port 9091? | error={}", phone, e.getMessage());
        }
    }

    private void createOrderFromQuotation(Quotation q) {
        try {
            List<Map<String, Object>> orderItems = new ArrayList<>();
            for (QuotationItem item : q.getItems()) {
                Map<String, Object> oi = new HashMap<>();
                oi.put("productId",   item.getProductId());
                oi.put("productName", item.getProductName());
                oi.put("sku",         item.getProductName() != null
                        ? item.getProductName().replaceAll("\\s+", "-").toUpperCase() : "ITEM");
                oi.put("unit",        item.getUnit() != null ? item.getUnit() : "piece");
                oi.put("quantity",    item.getQuantity());
                oi.put("unitPrice",   item.getQuotedPrice() != null ? item.getQuotedPrice() : BigDecimal.ZERO);
                oi.put("gstRate",     BigDecimal.valueOf(18));
                orderItems.add(oi);
            }
            Map<String, Object> orderReq = new HashMap<>();
            orderReq.put("userId",         q.getUserId());
            orderReq.put("userName",        q.getUserName());
            orderReq.put("userEmail",       q.getUserEmail());
            orderReq.put("customerPhone",   q.getUserPhone());
            orderReq.put("deliveryAddress", q.getCompanyName() != null ? q.getCompanyName() : "");
            orderReq.put("deliveryCity",    "Nashik");
            orderReq.put("deliveryPincode", "");
            orderReq.put("paymentMethod",   "BANK_TRANSFER");
            orderReq.put("notes",           "Auto-created from approved RFQ: " + q.getQuotationNumber()
                    + (q.getNotes() != null ? " | " + q.getNotes() : ""));
            orderReq.put("items",           orderItems);

            Map<?, ?> response = restTemplate.postForObject(ORDERS_URL, orderReq, Map.class);
            log.info("==> ORDER CREATED from quotation {} | orderNumber={}",
                    q.getQuotationNumber(), response != null ? response.get("orderNumber") : "unknown");

            if (response != null && response.get("totalAmount") != null) {
                q.setTotalAmount(new BigDecimal(response.get("totalAmount").toString()));
                quotationRepository.save(q);
            }
        } catch (Exception e) {
            log.warn("==> ORDER CREATION FAILED | quotation={}: {}", q.getQuotationNumber(), e.getMessage());
        }
    }
}
