package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LimitDto;
import org.example.dto.PaymentRequestDto;
import org.example.dto.PaymentResponseDto;
import org.example.exception.LimitReachedException;
import org.example.exception.NotFoundException;
import org.example.exception.PaymentServiceException;
import org.example.mapper.LimitMapper;
import org.example.model.LimitEntity;
import org.example.model.TransactionEntity;
import org.example.repository.LimitRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitService {
    private final LimitRepository limitRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;
    private final LimitMapper limitMapper;

    @Value("${limits.standard}")
    private BigDecimal amount;

    @Transactional
    public LimitDto getLimitByUserId(Long userId) {
        return limitMapper.limitEntityToLimitDto(getLimitByUserIdOrCreate(userId));
    }

    @Transactional
    public LimitDto executePaymentRequest(PaymentRequestDto paymentRequestDto) {
        LimitEntity limitEntity = getLimitByUserIdOrCreate(paymentRequestDto.userId());
        if (limitEntity.getLimitAmount().compareTo(paymentRequestDto.amount()) < 0) {
            throw new LimitReachedException("Достигнут лимит средств у пользователя с id = " + paymentRequestDto.userId());
        }
        PaymentResponseDto paymentResponseDto = paymentService.executePayment(paymentRequestDto);
        if (paymentResponseDto.isSucceed()) {
            limitEntity.setLimitAmount(limitEntity.getLimitAmount().subtract(paymentRequestDto.amount()));
            limitRepository.save(limitEntity);
            log.info("Лимит пользователя с id = {} уменьшен на сумму {}", paymentRequestDto.userId(), limitEntity.getLimitAmount());
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setPaymentId(paymentResponseDto.paymentId());
            transactionEntity.setAmount(paymentRequestDto.amount());
            transactionEntity.setUserId(limitEntity.getUserId());
            transactionRepository.save(transactionEntity);
            return limitMapper.limitEntityToLimitDto(limitEntity);
        } else {
            throw new PaymentServiceException(paymentResponseDto.message());
        }
    }

    private LimitEntity getLimitByUserIdOrCreate(Long userId) {
        Optional<LimitEntity> limitOptional = limitRepository.findByUserId(userId);
        if (limitOptional.isPresent()) {
            return limitOptional.get();
        }
        LimitEntity newLimit = new LimitEntity();
        newLimit.setUserId(userId);
        newLimit.setLimitAmount(amount);
        limitRepository.save(newLimit);
        log.info("Добавлен новый лимит для пользователя с id = {}", userId);
        return newLimit;
    }

    @Transactional
    public void revertPaymentRequest(UUID paymentId) {

        transactionRepository.findByPaymentId(paymentId)
                .ifPresentOrElse(transactionEntity -> {
                            LimitEntity revertingLimit = getLimitByUserIdOrCreate(transactionEntity.getUserId());
                            revertingLimit.setLimitAmount(
                                    revertingLimit
                                            .getLimitAmount()
                                            .add(transactionEntity.getAmount()));
                            limitRepository.save(revertingLimit);
                            transactionRepository.delete(transactionEntity);
                            log.info("Выполнен возврат средств, платёж с id = {} отменён", paymentId);
                        },
                        () -> {
                            throw new NotFoundException("Не найден платёж с id = " + paymentId);
                        });

    }
}
