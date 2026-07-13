package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LimitDto;
import org.example.dto.LimitOperationRequestDto;
import org.example.exception.LimitReachedException;
import org.example.exception.NotFoundException;
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
    private final LimitMapper limitMapper;

    @Value("${limits.standard}")
    private BigDecimal amount;

    @Transactional
    public LimitDto getLimitByUserId(Long userId) {
        return limitMapper.limitEntityToLimitDto(getLimitByUserIdOrCreate(userId));
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
    public LimitDto decreaseLimit(LimitOperationRequestDto request) {
        LimitEntity limitEntity =
                getLimitByUserIdOrCreate(request.userId());

        if (limitEntity.getLimitAmount()
                .compareTo(request.amount()) < 0) {

            throw new LimitReachedException(
                    "Недостаточно доступного лимита у пользователя с id = "
                            + request.userId()
            );
        }

        limitEntity.setLimitAmount(
                limitEntity.getLimitAmount()
                        .subtract(request.amount())
        );

        TransactionEntity transactionEntity =
                new TransactionEntity();

        transactionEntity.setOperationId(request.operationId());
        transactionEntity.setAmount(request.amount());
        transactionEntity.setUserId(request.userId());

        limitRepository.save(limitEntity);
        transactionRepository.save(transactionEntity);

        log.info(
                "Лимит пользователя с id = {} уменьшен на {}",
                request.userId(),
                request.amount()
        );

        return limitMapper.limitEntityToLimitDto(limitEntity);
    }

    @Transactional
    public LimitDto revertLimit(UUID operationId) {
        TransactionEntity transaction =
                transactionRepository.findByOperationId(operationId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Не найдена операция с id = "
                                                + operationId
                                )
                        );

        LimitEntity limit =
                getLimitByUserIdOrCreate(transaction.getUserId());

        limit.setLimitAmount(
                limit.getLimitAmount()
                        .add(transaction.getAmount())
        );

        limitRepository.save(limit);
        transactionRepository.delete(transaction);

        log.info(
                "Лимит возвращён по операции с id = {}",
                operationId
        );

        return limitMapper.limitEntityToLimitDto(limit);
    }
}
