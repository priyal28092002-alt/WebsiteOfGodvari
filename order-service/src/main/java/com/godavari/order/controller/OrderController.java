package com.godavari.order.controller;

import com.godavari.order.dto.OrderRequest;
import com.godavari.order.model.Order;
import com.godavari.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req) {
        log.info("==> CREATE ORDER | userId={} items={} paymentMethod={}",
                req.getUserId(), req.getItems() != null ? req.getItems().size() : 0, req.getPaymentMethod());
        try {
            Order order = orderService.createOrder(req);
            log.info("==> CREATE ORDER SUCCESS | orderNumber={} total={}", order.getOrderNumber(), order.getTotalAmount());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("==> CREATE ORDER FAILED | userId={}: {}", req.getUserId(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        log.info("==> GET ORDER | id={}", id);
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<Order> getByNumber(@PathVariable String orderNumber) {
        log.info("==> GET ORDER | orderNumber={}", orderNumber);
        return ResponseEntity.ok(orderService.getByOrderNumber(orderNumber));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Order>> getByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("==> GET ORDERS BY USER | userId={} page={}", userId, page);
        Page<Order> orders = orderService.getOrdersByUser(userId, page, size);
        log.info("==> GET ORDERS BY USER SUCCESS | userId={} count={}", userId, orders.getTotalElements());
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("==> GET ALL ORDERS | page={} size={}", page, size);
        Page<Order> orders = orderService.getAllOrders(page, size);
        log.info("==> GET ALL ORDERS SUCCESS | total={}", orders.getTotalElements());
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam String status) {
        log.info("==> UPDATE ORDER STATUS | id={} newStatus={}", id, status);
        try {
            Order order = orderService.updateStatus(id, status);
            log.info("==> UPDATE ORDER STATUS SUCCESS | id={} status={}", id, status);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("==> UPDATE ORDER STATUS FAILED | id={}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}/payment-status")
    public ResponseEntity<Order> updatePaymentStatus(@PathVariable Long id, @RequestParam String paymentStatus) {
        log.info("==> UPDATE ORDER PAYMENT STATUS | id={} paymentStatus={}", id, paymentStatus);
        try {
            Order order = orderService.updatePaymentStatus(id, paymentStatus);
            log.info("==> UPDATE ORDER PAYMENT STATUS SUCCESS | id={}", id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("==> UPDATE ORDER PAYMENT STATUS FAILED | id={}: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        log.info("==> GET DASHBOARD STATS");
        Map<String, Object> stats = orderService.getDashboardStats();
        log.info("==> GET DASHBOARD STATS SUCCESS | {}", stats);
        return ResponseEntity.ok(stats);
    }
}
