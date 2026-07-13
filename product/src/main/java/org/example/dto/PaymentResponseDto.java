package org.example.dto;

import java.io.Serializable;

public record PaymentResponseDto(
        boolean isSucceed,
        String message) implements Serializable {
}
