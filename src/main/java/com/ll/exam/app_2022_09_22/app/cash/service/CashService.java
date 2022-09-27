package com.ll.exam.app_2022_09_22.app.cash.service;

import com.ll.exam.app_2022_09_22.app.cash.entity.CashLog;
import com.ll.exam.app_2022_09_22.app.cash.repository.CashLogRepository;
import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashService {
    private final CashLogRepository cashLogRepository;

    public CashLog addCash(Member member, long price, String eventType) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                .eventType(eventType)
                .build();

        cashLogRepository.save(cashLog);

        return cashLog;
    }
}
