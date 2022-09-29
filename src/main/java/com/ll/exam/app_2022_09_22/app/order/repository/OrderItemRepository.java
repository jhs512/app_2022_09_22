package com.ll.exam.app_2022_09_22.app.order.repository;

import com.ll.exam.app_2022_09_22.app.order.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Page<OrderItem> findAllByIdLessThan(long id, Pageable pageable);
}