package com.example.invoicesystem.controller;

import com.example.invoicesystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/invoiceInquiry/{subscribeNo}")
    public ResponseEntity<?> invoiceInquiry(@PathVariable Long subscribeNo) {
        return this.paymentService.invoiceInquiry(subscribeNo);
    }
}
