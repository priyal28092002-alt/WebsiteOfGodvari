package com.godavari.quotation.controller;

import com.godavari.quotation.model.Quotation;
import com.godavari.quotation.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quotations")
public class QuotationController {

    private static final Logger log = LoggerFactory.getLogger(QuotationController.class);

    @Autowired private QuotationService quotationService;

    @PostMapping
    public ResponseEntity<Quotation> create(@RequestBody Quotation quotation) {
        log.info("==> CREATE QUOTATION | userId={} company={} items={}",
                quotation.getUserId(), quotation.getCompanyName(),
                quotation.getItems() != null ? quotation.getItems().size() : 0);
        try {
            Quotation saved = quotationService.create(quotation);
            log.info("==> CREATE QUOTATION SUCCESS | id={} number={}", saved.getId(), saved.getQuotationNumber());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            log.error("==> CREATE QUOTATION FAILED | userId={}: {}", quotation.getUserId(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quotation> getById(@PathVariable Long id) {
        log.info("==> GET QUOTATION | id={}", id);
        return ResponseEntity.ok(quotationService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Quotation>> getByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("==> GET QUOTATIONS BY USER | userId={} page={}", userId, page);
        Page<Quotation> result = quotationService.getByUser(userId, page, size);
        log.info("==> GET QUOTATIONS BY USER SUCCESS | userId={} count={}", userId, result.getTotalElements());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Page<Quotation>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("==> GET ALL QUOTATIONS | page={} size={}", page, size);
        Page<Quotation> result = quotationService.getAll(page, size);
        log.info("==> GET ALL QUOTATIONS SUCCESS | total={}", result.getTotalElements());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Quotation> updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String adminRemarks) {
        log.info("==> UPDATE QUOTATION STATUS | id={} newStatus={} remarks={}", id, status, adminRemarks);
        try {
            Quotation updated = quotationService.updateStatus(id, status, adminRemarks);
            log.info("==> UPDATE QUOTATION STATUS SUCCESS | id={} status={}", id, status);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("==> UPDATE QUOTATION STATUS FAILED | id={}: {}", id, e.getMessage());
            throw e;
        }
    }
}
