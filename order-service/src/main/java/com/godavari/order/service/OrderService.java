package com.godavari.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.godavari.order.dto.OrderRequest;
import com.godavari.order.model.Order;
import com.godavari.order.model.OrderItem;
import com.godavari.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired private OrderRepository orderRepository;
    @Autowired(required = false) private KafkaTemplate<String, String> kafkaTemplate;

    private static final String ORDER_PLACED_TOPIC = "order.placed";
    private static final String BILLING_URL        = "http://localhost:8087/billing/invoices";
    private static final String INVENTORY_URL      = "http://localhost:8087/inventory/update";
    private static final String WHATSAPP_URL       = "http://localhost:9091/send";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper  = new ObjectMapper().registerModule(new JavaTimeModule());

    @Transactional
    public Order createOrder(OrderRequest req) {
        Order order = new Order();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setUserId(req.getUserId());
        order.setUserName(req.getUserName());
        order.setUserEmail(req.getUserEmail());
        order.setDeliveryAddress(req.getDeliveryAddress());
        order.setDeliveryCity(req.getDeliveryCity());
        order.setDeliveryPincode(req.getDeliveryPincode());
        order.setPaymentMethod(req.getPaymentMethod());
        order.setNotes(req.getNotes());
        order.setCustomerPhone(req.getCustomerPhone());
        order.setExpectedDeliveryDate(LocalDate.now().plusDays(5));

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalGst = BigDecimal.ZERO;

        for (OrderRequest.OrderItemRequest itemReq : req.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemReq.getProductId());
            item.setProductName(itemReq.getProductName());
            item.setSku(itemReq.getSku());
            item.setUnit(itemReq.getUnit());
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(itemReq.getUnitPrice());
            item.setGstRate(itemReq.getGstRate());

            BigDecimal lineTotal = itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            BigDecimal gst = lineTotal.multiply(itemReq.getGstRate()).divide(BigDecimal.valueOf(100));
            item.setGstAmount(gst);
            item.setTotalPrice(lineTotal.add(gst));

            subtotal = subtotal.add(lineTotal);
            totalGst = totalGst.add(gst);
            order.getItems().add(item);
        }

        order.setSubtotal(subtotal);
        order.setGstAmount(totalGst);
        order.setDeliveryCharge(subtotal.compareTo(BigDecimal.valueOf(5000)) >= 0
                ? BigDecimal.ZERO : BigDecimal.valueOf(500));
        order.setTotalAmount(subtotal.add(totalGst)
                .add(order.getDeliveryCharge())
                .subtract(order.getDiscountAmount()));

        Order saved = orderRepository.save(order);

        deductInventory(saved);
        notifyOrderPlaced(saved);

        return saved;
    }

    private void notifyOrderPlaced(Order order) {
        if (kafkaTemplate != null) {
            publishOrderKafka(order);
        } else {
            log.warn("==> Kafka not available, sending WhatsApp directly");
            sendOrderWhatsApp(order);
        }
    }

    private void publishOrderKafka(Order order) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("orderId",             order.getId());
            event.put("orderNumber",         order.getOrderNumber());
            event.put("customerName",        order.getUserName());
            event.put("customerEmail",       order.getUserEmail());
            event.put("customerPhone",       order.getCustomerPhone() != null ? order.getCustomerPhone() : "");
            event.put("deliveryAddress",     order.getDeliveryAddress());
            event.put("deliveryCity",        order.getDeliveryCity());
            event.put("paymentMethod",       order.getPaymentMethod());
            event.put("subtotal",            order.getSubtotal());
            event.put("gstAmount",           order.getGstAmount());
            event.put("deliveryCharge",      order.getDeliveryCharge());
            event.put("totalAmount",         order.getTotalAmount());
            event.put("expectedDeliveryDate",order.getExpectedDeliveryDate() != null ? order.getExpectedDeliveryDate().toString() : "");
            List<Map<String, Object>> items = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                Map<String, Object> i = new HashMap<>();
                i.put("productName", item.getProductName());
                i.put("quantity",    item.getQuantity());
                i.put("unit",        item.getUnit());
                i.put("unitPrice",   item.getUnitPrice());
                i.put("totalPrice",  item.getTotalPrice());
                i.put("gstRate",     item.getGstRate());
                items.add(i);
            }
            event.put("items", items);
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(ORDER_PLACED_TOPIC, order.getOrderNumber(), json);
            log.info("==> KAFKA PUBLISHED | topic={} orderNumber={}", ORDER_PLACED_TOPIC, order.getOrderNumber());
        } catch (Exception e) {
            log.warn("==> KAFKA PUBLISH FAILED | {} — falling back to direct WhatsApp: {}", order.getOrderNumber(), e.getMessage());
            sendOrderWhatsApp(order);
        }
    }

    private void sendOrderWhatsApp(Order order) {
        String phone = order.getCustomerPhone();
        if (phone == null || phone.isBlank()) {
            log.warn("==> WHATSAPP SKIP | No phone for order {}", order.getOrderNumber());
            return;
        }
        try {
            StringBuilder msg = new StringBuilder();
            msg.append("🛒 *Order Confirmed!*\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("📦 Order: *").append(order.getOrderNumber()).append("*\n");
            msg.append("👤 Name: ").append(order.getUserName()).append("\n");
            msg.append("📍 Delivery: ").append(order.getDeliveryAddress()).append(", ").append(order.getDeliveryCity()).append("\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            for (OrderItem item : order.getItems()) {
                msg.append("• ").append(item.getProductName())
                   .append(" x").append(item.getQuantity())
                   .append(" ").append(item.getUnit() != null ? item.getUnit() : "")
                   .append(" = ₹").append(item.getTotalPrice()).append("\n");
            }
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("💰 Subtotal: ₹").append(order.getSubtotal()).append("\n");
            msg.append("📊 GST: ₹").append(order.getGstAmount()).append("\n");
            msg.append("🚚 Delivery: ₹").append(order.getDeliveryCharge()).append("\n");
            msg.append("💵 *Total: ₹").append(order.getTotalAmount()).append("*\n");
            msg.append("━━━━━━━━━━━━━━━━━━━━\n");
            msg.append("📅 Expected: ").append(order.getExpectedDeliveryDate()).append("\n");
            msg.append("💳 Payment: ").append(order.getPaymentMethod()).append("\n\n");
            msg.append("Thank you! - Godavari Building Materials, Nashik");

            sendWhatsApp(phone, msg.toString(), order.getOrderNumber());
        } catch (Exception e) {
            log.warn("==> WHATSAPP ORDER MSG BUILD FAILED | {}: {}", order.getOrderNumber(), e.getMessage());
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

    private void deductInventory(Order order) {
        for (OrderItem item : order.getItems()) {
            try {
                Map<String, Object> req = new HashMap<>();
                req.put("productId",       item.getProductId());
                req.put("productName",     item.getProductName());
                req.put("transactionType", "OUT");
                req.put("quantity",        item.getQuantity());
                req.put("unit",            item.getUnit());
                req.put("referenceId",     order.getOrderNumber());
                req.put("notes",           "Order placed: " + order.getOrderNumber());
                req.put("createdBy",       order.getUserName());
                restTemplate.postForObject(INVENTORY_URL, req, Map.class);
                log.info("==> INVENTORY DEDUCTED | productId={}", item.getProductId());
            } catch (Exception e) {
                log.warn("==> INVENTORY DEDUCTION FAILED | productId={}: {}", item.getProductId(), e.getMessage());
            }
        }
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public Order getByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new RuntimeException("Order not found: " + orderNumber));
    }

    public Page<Order> getOrdersByUser(Long userId, int page, int size) {
        return orderRepository.findByUserId(userId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<Order> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Order updateStatus(Long id, String status) {
        Order order = getById(id);
        Order.OrderStatus newStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);
        if (newStatus == Order.OrderStatus.CONFIRMED) generateInvoice(saved);
        return saved;
    }

    public Order updatePaymentStatus(Long id, String paymentStatus) {
        Order order = getById(id);
        Order.PaymentStatus ps = Order.PaymentStatus.valueOf(paymentStatus.toUpperCase());
        order.setPaymentStatus(ps);
        log.info("==> PAYMENT STATUS UPDATED | orderId={} paymentStatus={}", id, paymentStatus);
        return orderRepository.save(order);
    }

    private void generateInvoice(Order order) {
        try {
            Map<String, Object> req = new HashMap<>();
            req.put("orderId",        order.getId());
            req.put("orderNumber",    order.getOrderNumber());
            req.put("userId",         order.getUserId());
            req.put("userName",       order.getUserName());
            req.put("userEmail",      order.getUserEmail());
            req.put("userPhone",      order.getCustomerPhone());
            req.put("billingAddress", order.getDeliveryAddress() + ", " + order.getDeliveryCity() + " - " + order.getDeliveryPincode());
            req.put("paymentMethod",  order.getPaymentMethod());
            req.put("subtotal",       order.getSubtotal());
            req.put("gstAmount",      order.getGstAmount());
            req.put("totalAmount",    order.getTotalAmount());
            req.put("interState",     false);
            Map<?, ?> response = restTemplate.postForObject(BILLING_URL, req, Map.class);
            log.info("==> INVOICE GENERATED | invoiceNumber={}", response != null ? response.get("invoiceNumber") : "unknown");
        } catch (Exception e) {
            log.warn("==> INVOICE GENERATION FAILED | orderNumber={}: {}", order.getOrderNumber(), e.getMessage());
        }
    }

    public Map<String, Object> getDashboardStats() {
        LocalDateTime monthStart = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return Map.of(
            "totalOrdersThisMonth", orderRepository.getOrderCount(monthStart),
            "revenueThisMonth",     orderRepository.getTotalRevenue(monthStart),
            "pendingOrders",        orderRepository.findByStatus(Order.OrderStatus.PENDING).size(),
            "totalOrders",          orderRepository.count()
        );
    }
}
