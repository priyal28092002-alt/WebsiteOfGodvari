package com.godavari.billing.controller;

import com.godavari.billing.dto.InvoiceRequest;
import com.godavari.billing.model.Invoice;
import com.godavari.billing.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private static final Logger log = LoggerFactory.getLogger(BillingController.class);

    @Autowired private BillingService billingService;

    @PostMapping("/invoices")
    public ResponseEntity<Invoice> generate(@RequestBody InvoiceRequest req) {
        log.info("==> GENERATE INVOICE | orderId={} userId={}", req.getOrderId(), req.getUserId());
        try {
            Invoice invoice = billingService.generateInvoice(req);
            log.info("==> GENERATE INVOICE SUCCESS | invoiceNumber={} total={}", invoice.getInvoiceNumber(), invoice.getTotalAmount());
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            log.error("==> GENERATE INVOICE FAILED | orderId={}: {}", req.getOrderId(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAll() {
        log.info("==> GET ALL INVOICES");
        List<Invoice> invoices = billingService.getAll();
        log.info("==> GET ALL INVOICES SUCCESS | count={}", invoices.size());
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getById(@PathVariable Long id) {
        log.info("==> GET INVOICE | id={}", id);
        return ResponseEntity.ok(billingService.getById(id));
    }

    @GetMapping("/invoices/order/{orderId}")
    public ResponseEntity<Invoice> getByOrder(@PathVariable Long orderId) {
        log.info("==> GET INVOICE BY ORDER | orderId={}", orderId);
        return ResponseEntity.ok(billingService.getByOrderId(orderId));
    }

    @GetMapping("/invoices/user/{userId}")
    public ResponseEntity<List<Invoice>> getByUser(@PathVariable Long userId) {
        log.info("==> GET INVOICES BY USER | userId={}", userId);
        List<Invoice> invoices = billingService.getByUserId(userId);
        log.info("==> GET INVOICES BY USER SUCCESS | userId={} count={}", userId, invoices.size());
        return ResponseEntity.ok(invoices);
    }

    @PutMapping("/invoices/{id}/pay")
    public ResponseEntity<Invoice> markPaid(@PathVariable Long id) {
        log.info("==> MARK INVOICE PAID | id={}", id);
        try {
            Invoice invoice = billingService.markAsPaid(id);
            log.info("==> MARK INVOICE PAID SUCCESS | id={} invoiceNumber={}", id, invoice.getInvoiceNumber());
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            log.error("==> MARK INVOICE PAID FAILED | id={}: {}", id, e.getMessage());
            throw e;
        }
    }
}
