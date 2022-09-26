package com.ll.exam.app_2022_09_22.app.cart.repository;

import com.ll.exam.app_2022_09_22.app.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
