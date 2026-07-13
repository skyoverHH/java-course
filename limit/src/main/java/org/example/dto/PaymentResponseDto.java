package org.example.dto;

import java.io.Serializable;
import java.util.UUID;

public record PaymentResponseDto(
        boolean isSucceed,
        UUID paymentId,
        String message) implements Serializable {
}
