package org.example.dto;

import java.io.Serializable;

public record ErrorResponseDto(String message) implements Serializable {
}
