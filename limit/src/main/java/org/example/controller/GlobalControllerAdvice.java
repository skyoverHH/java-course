package org.example.controller;

import org.example.dto.ErrorResponseDto;
import org.example.exception.LimitReachedException;
import org.example.exception.NotFoundException;
import org.example.exception.PaymentServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(PaymentServiceException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponseDto handlePaymentServiceException(PaymentServiceException exception) {
        return new ErrorResponseDto(exception.getMessage());
    }

    @ExceptionHandler(LimitReachedException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponseDto handleLimitReachedException(LimitReachedException exception) {
        return new ErrorResponseDto(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotFoundException(NotFoundException exception) {
        return new ErrorResponseDto(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleRuntimeException(Exception exception) {
        return new ErrorResponseDto(exception.getMessage());
    }

}
