package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PaymentRequestDto;
import org.example.dto.PaymentResponseDto;
import org.example.dto.ProductListDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductClientService {

    private final RestTemplate restTemplate;

    public ProductListDto getProductsByUser(Long userId) {
        log.info("Выполнение запроса на получение всех продуктов для пользователя с id: {}", userId);
        return restTemplate.getForObject("/products?userId={userId}", ProductListDto.class, userId);
    }

    public PaymentResponseDto executePayment(PaymentRequestDto paymentRequest) {
        log.info("Выполнение запроса на обработку оплаты для пользователя с id: {}", paymentRequest.userId());
        return restTemplate.postForObject("/products/payment", paymentRequest, PaymentResponseDto.class);
    }
}
