package com.godavari.product.controller;

import com.godavari.product.model.Category;
import com.godavari.product.model.Product;
import com.godavari.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search) {

        log.info("==> GET PRODUCTS | search={} categoryId={} page={} size={}", search, categoryId, page, size);
        try {
            Page<Product> result;
            if (search != null && !search.isBlank())
                result = productService.search(search, page, size);
            else if (categoryId != null)
                result = productService.getByCategory(categoryId, page, size);
            else
                result = productService.getAllProducts(page, size, sortBy);
            log.info("==> GET PRODUCTS SUCCESS | returned {} items", result.getTotalElements());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("==> GET PRODUCTS FAILED: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        log.info("==> GET PRODUCT | id={}", id);
        return ResponseEntity.ok(productService.getById(id));
    }

    /**
     * Accepts a request body that may contain either:
     * - A full Product JSON with nested category object  { "category": { "id": 1 } }
     * - A flat DTO with categoryId field                 { "categoryId": 1 }
     * This handles the Angular frontend sending categoryId as a plain number.
     */
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Map<String, Object> body) {
        log.info("==> CREATE PRODUCT | name={} price={}", body.get("name"), body.get("price"));
        try {
            Product product = mapToProduct(body, new Product());
            Product saved = productService.save(product);
            log.info("==> CREATE PRODUCT SUCCESS | id={} name={}", saved.getId(), saved.getName());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            log.error("==> CREATE PRODUCT FAILED: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        log.info("==> UPDATE PRODUCT | id={} name={}", id, body.get("name"));
        try {
            Product product = productService.getById(id);
            mapToProduct(body, product);
            product.setId(id);
            Product saved = productService.save(product);
            log.info("==> UPDATE PRODUCT SUCCESS | id={}", id);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            log.error("==> UPDATE PRODUCT FAILED | id={}: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("==> DELETE PRODUCT | id={}", id);
        try {
            productService.delete(id);
            log.info("==> DELETE PRODUCT SUCCESS | id={}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("==> DELETE PRODUCT FAILED | id={}: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        log.info("==> GET CATEGORIES");
        return ResponseEntity.ok(productService.getAllCategories());
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        log.info("==> CREATE CATEGORY | name={}", category.getName());
        try {
            Category saved = productService.saveCategory(category);
            log.info("==> CREATE CATEGORY SUCCESS | id={} name={}", saved.getId(), saved.getName());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            log.error("==> CREATE CATEGORY FAILED: {}", e.getMessage());
            throw e;
        }
    }

    // ── Helper: maps flat request body (with categoryId) to Product entity ──
    private Product mapToProduct(Map<String, Object> body, Product product) {
        if (body.get("name") != null)
            product.setName(body.get("name").toString());

        if (body.get("sku") != null)
            product.setSku(body.get("sku").toString());

        if (body.get("brand") != null)
            product.setBrand(body.get("brand").toString());

        if (body.get("unit") != null)
            product.setUnit(body.get("unit").toString());

        if (body.get("hsnCode") != null)
            product.setHsnCode(body.get("hsnCode").toString());

        if (body.get("imageUrl") != null)
            product.setImageUrl(body.get("imageUrl").toString());

        if (body.get("description") != null)
            product.setDescription(body.get("description").toString());

        if (body.get("price") != null)
            product.setPrice(new java.math.BigDecimal(body.get("price").toString()));

        if (body.get("mrp") != null && !body.get("mrp").toString().equals("null"))
            product.setMrp(new java.math.BigDecimal(body.get("mrp").toString()));

        if (body.get("gstRate") != null)
            product.setGstRate(new java.math.BigDecimal(body.get("gstRate").toString()));

        if (body.get("minOrderQty") != null)
            product.setMinOrderQty(Integer.valueOf(body.get("minOrderQty").toString()));

        if (body.get("bulkMinQty") != null)
            product.setBulkMinQty(Integer.valueOf(body.get("bulkMinQty").toString()));

        if (body.get("bulkDiscountPercent") != null)
            product.setBulkDiscountPercent(new java.math.BigDecimal(body.get("bulkDiscountPercent").toString()));

        // Resolve category — frontend sends categoryId as a number
        Object catId = body.get("categoryId");
        if (catId == null) {
            // Maybe frontend sent nested: { "category": { "id": 1 } }
            Object catObj = body.get("category");
            if (catObj instanceof Map) {
                Object nestedId = ((Map<?, ?>) catObj).get("id");
                if (nestedId != null) catId = nestedId;
            }
        }
        if (catId != null) {
            Long categoryId = Long.valueOf(catId.toString());
            Category category = productService.getCategoryById(categoryId);
            product.setCategory(category);
        }

        return product;
    }
}
