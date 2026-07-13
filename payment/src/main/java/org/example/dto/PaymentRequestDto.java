package org.example.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record PaymentRequestDto(
        Long userId,
        Long productId,
        BigDecimal amount) implements Serializable {
}
