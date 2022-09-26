package com.ll.exam.app_2022_09_22.app.product.repository;

import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
