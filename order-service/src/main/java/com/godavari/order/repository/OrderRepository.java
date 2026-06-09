package com.godavari.order.repository;

import com.godavari.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByStatus(Order.OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.createdAt >= :from AND o.status != 'CANCELLED'")
    BigDecimal getTotalRevenue(LocalDateTime from);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt >= :from")
    Long getOrderCount(LocalDateTime from);
}
