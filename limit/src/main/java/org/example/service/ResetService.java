package org.example.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ResetService {

    private final LimitRepository limitRepository;

    @Value("${limits.standard}")
    private BigDecimal amount;

    public ResetService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @Scheduled(cron = "${scheduler.limits_reset_cron}")
    @Transactional
    public void resetLimits() {
        //log.info("Сброс лимитов всех пользователей на значение {}", amount);
        limitRepository.resetDailyLimits(amount);
    }
}
