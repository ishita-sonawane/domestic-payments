package com.onlinebanking.domesticpayments.controller;

import com.onlinebanking.domesticpayments.dto.DomesticPaymentRequest;
import com.onlinebanking.domesticpayments.dto.DomesticPaymentResponse;
import com.onlinebanking.domesticpayments.error.InsufficientBalanceException;
import com.onlinebanking.domesticpayments.error.IncorrectBeneficiaryDetailsException;
import com.onlinebanking.domesticpayments.model.DomesticPayment;
import com.onlinebanking.domesticpayments.service.DomesticPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/domestic-payments")
public class DomesticPaymentController {

    @Autowired
    private DomesticPaymentService paymentService;

    @PostMapping
    public ResponseEntity<DomesticPaymentResponse> createPayment(@RequestBody DomesticPaymentRequest request) {
        System.out.println("Received payment request: " + request);

        // Check for sufficient balance
        if (request.getPaymentAmount() != null && request.getPaymentAmount().doubleValue() > 10000) {
            throw new InsufficientBalanceException("Insufficient balance for this transaction.");
        }

        // Validate beneficiary details
        if (request.getPayeeAccountNumber() == null || request.getPayeeAccountNumber().isEmpty()) {
            throw new IncorrectBeneficiaryDetailsException("Beneficiary account number is required.");
        }

        // Persist payment using service
        DomesticPayment payment = paymentService.savePayment(request);

        // Build response
        DomesticPaymentResponse response = new DomesticPaymentResponse();
        response.setPaymentReferenceId(payment.getPaymentReferenceId());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setPaymentStatusReason(payment.getPaymentStatusReason());

        return ResponseEntity.ok(response);
    }
}