package com.godavari.quotation.repository;

import com.godavari.quotation.model.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    Page<Quotation> findByUserId(Long userId, Pageable pageable);
    Optional<Quotation> findByQuotationNumber(String quotationNumber);
}
