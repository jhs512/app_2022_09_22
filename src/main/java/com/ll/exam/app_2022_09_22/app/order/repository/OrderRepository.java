package com.ll.exam.app_2022_09_22.app.order.repository;

import com.ll.exam.app_2022_09_22.app.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
