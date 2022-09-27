package com.ll.exam.app_2022_09_22.app.product.repository;

import com.ll.exam.app_2022_09_22.app.product.entity.BackupedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackupedProductRepository extends JpaRepository<BackupedProduct, Long> {
}

