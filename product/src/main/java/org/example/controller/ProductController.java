package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.PaymentRequestDto;
import org.example.dto.PaymentResponseDto;
import org.example.dto.ProductListDto;
import org.example.dto.ProductDto;
import org.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/account/{accountNumber}")
    public ProductDto getProductByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return productService.getByAccountNumber(accountNumber);
    }

    @GetMapping
    public ProductListDto getAllByUserId(@RequestParam("userId") Long id) {
        return productService.getAllByUserId(id);
    }

    @PostMapping("/payment")
    public PaymentResponseDto executePayment(@RequestBody PaymentRequestDto paymentRequest) {
        log.info("Получен запрос на выполнение оплаты для пользователя с id: {}", paymentRequest.userId());
        return productService.executePayment(paymentRequest);
    }
}
