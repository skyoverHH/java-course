package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LimitDto;
import org.example.dto.PaymentRequestDto;
import org.example.service.LimitService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "limits/v1")
@RequiredArgsConstructor
public class LimitController {
    private final LimitService limitService;

    @GetMapping("/user/{userId}")
    public LimitDto getUserLimit(@PathVariable("userId") Long userId) {
        return limitService.getLimitByUserId(userId);
    }

    @PostMapping("/payment")
    public LimitDto performPaymentRequest(@RequestBody PaymentRequestDto paymentRequest) {
        return limitService.executePaymentRequest(paymentRequest);
    }

    @PostMapping("/payment/revert/{paymentId}")
    public void revertPaymentRequest(@PathVariable("paymentId") UUID paymentId) {
        limitService.revertPaymentRequest(paymentId);
    }
}

