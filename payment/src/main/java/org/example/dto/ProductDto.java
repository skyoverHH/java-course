package org.example.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String accountNumber,
        BigDecimal balance,
        String type) implements Serializable {
}
