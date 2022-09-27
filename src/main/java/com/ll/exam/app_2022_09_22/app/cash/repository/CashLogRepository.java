package com.ll.exam.app_2022_09_22.app.cash.repository;

import com.ll.exam.app_2022_09_22.app.cash.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
}
