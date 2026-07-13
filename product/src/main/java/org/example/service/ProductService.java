package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.PaymentRequestDto;
import org.example.dto.PaymentResponseDto;
import org.example.dto.ProductListDto;
import org.example.dto.ProductDto;
import org.example.model.ProductEntity;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getByAccountNumber(String accountNumber) {
        return productRepository.findByAccountNumber(accountNumber)
                .map(this::productToDto)
                .orElseThrow(() -> new NoSuchElementException("Не найдена запись Product c accountNumber = " + accountNumber));
    }

    public ProductListDto getAllByUserId(Long id) {
        return new ProductListDto(productRepository.findAllByUserId(id)
                .stream()
                .map(this::productToDto)
                .toList());
    }

    private ProductDto productToDto(ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getAccountNumber(), productEntity.getBalance(), productEntity.getType());
    }

    @Transactional
    public PaymentResponseDto executePayment(PaymentRequestDto paymentRequest) {
        var productOptional = productRepository.findAllByUserId(paymentRequest.userId())
                .stream()
                .filter(product -> Objects.equals(product.getId(), paymentRequest.productId()))
                .findFirst();
        if (productOptional.isEmpty()) {
            return new PaymentResponseDto(false,
                    "Продукт с id = " + paymentRequest.productId() +
                            " отсутствует у пользователя с id = " + paymentRequest.userId());
        }

        ProductEntity product = productOptional.get();
        if (product.getBalance().compareTo(paymentRequest.amount()) < 0) {
            return new PaymentResponseDto(false, "Недостаточное количество средств на балансе");
        }

        product.setBalance(product.getBalance().subtract(paymentRequest.amount()));

        return new PaymentResponseDto(true, "Платеж успешно выполнен");
    }
}
