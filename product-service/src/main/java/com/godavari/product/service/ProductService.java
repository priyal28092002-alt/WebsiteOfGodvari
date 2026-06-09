package com.godavari.product.service;

import com.godavari.product.exception.ResourceNotFoundException;
import com.godavari.product.model.Category;
import com.godavari.product.model.Product;
import com.godavari.product.repository.CategoryRepository;
import com.godavari.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    // No caching on Page objects — PageImpl cannot be deserialized by Redis
    // Redis caching is used for single product lookups and category lists only

    public Page<Product> getAllProducts(int page, int size, String sortBy) {
        return productRepository.findByIsActiveTrue(
            PageRequest.of(page, size, Sort.by(sortBy).descending()));
    }

    public Page<Product> getByCategory(Long categoryId, int page, int size) {
        return productRepository.findByCategoryIdAndIsActiveTrue(
            categoryId, PageRequest.of(page, size));
    }

    public Page<Product> search(String query, int page, int size) {
        return productRepository.search(query, PageRequest.of(page, size));
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    public Product save(Product product) {
        if (product.getId() == null && productRepository.existsBySku(product.getSku()))
            throw new IllegalArgumentException("SKU already exists: " + product.getSku());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product p = getById(id);
        p.setActive(false);
        productRepository.save(p);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        if (categoryRepository.existsByName(category.getName()))
            throw new IllegalArgumentException("Category already exists: " + category.getName());
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }
}
