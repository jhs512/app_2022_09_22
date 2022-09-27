package com.ll.exam.app_2022_09_22.app.product.repository;

import com.ll.exam.app_2022_09_22.app.product.entity.ProductBackup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductBackupRepository extends JpaRepository<ProductBackup, Long> {
    Optional<ProductBackup> findByProductId(Long id);
}
