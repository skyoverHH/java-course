package org.example.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PaymentRequestDto;
import org.example.dto.PaymentResponseDto;
import org.example.service.ProductClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final ProductClientService productClientService;

    @PostMapping("/execute")
    public PaymentResponseDto executePayment(@RequestBody PaymentRequestDto paymentRequest) {
        log.info("Получен запрос на выполнение оплаты для пользователя с id: {}", paymentRequest.userId());
        return productClientService.executePayment(paymentRequest);
    }
}
