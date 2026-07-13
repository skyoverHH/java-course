package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductListDto;
import org.example.service.ProductClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductClientService productClientService;

    @GetMapping("/user/{userId}")
    public ProductListDto getProductsByUser(@PathVariable Long userId) {
        log.info("Получен запрос получения продуктов по пользователю с id: {}", userId);

        return productClientService.getProductsByUser(userId);
    }
}
