package com.ll.exam.app_2022_09_22.app.rebate.repository;

import com.ll.exam.app_2022_09_22.app.rebate.entity.RebatedOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebatedOrderItemRepository extends JpaRepository<RebatedOrderItem, Long> {
}
