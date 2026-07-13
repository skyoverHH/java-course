package org.example.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record LimitOperationRequestDto(
        UUID operationId,
        Long userId,
        BigDecimal amount
) {
}
