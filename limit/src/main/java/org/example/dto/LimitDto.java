package org.example.dto;

import java.math.BigDecimal;

public record LimitDto(Long id, Long userId, BigDecimal limitAmount) {
}
