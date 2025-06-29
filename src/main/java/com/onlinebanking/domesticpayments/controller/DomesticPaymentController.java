package com.onlinebanking.domesticpayments.controller;

import com.onlinebanking.domesticpayments.dto.DomesticPaymentRequest;
import com.onlinebanking.domesticpayments.dto.DomesticPaymentResponse;
import com.onlinebanking.domesticpayments.error.InsufficientBalanceException;
import com.onlinebanking.domesticpayments.error.IncorrectBeneficiaryDetailsException;
import com.onlinebanking.domesticpayments.model.DomesticPayment;
import com.onlinebanking.domesticpayments.service.DomesticPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/domestic-payments")
public class DomesticPaymentController {

    private static final Logger logger = LoggerFactory.getLogger(DomesticPaymentController.class);

    private final DomesticPaymentService paymentService;

    public DomesticPaymentController(DomesticPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<DomesticPaymentResponse> createPayment(@RequestBody DomesticPaymentRequest request) {
        logger.info("Received payment request: {}", request);
        // Check for sufficient balance
        if (request.getPaymentAmount() != null && request.getPaymentAmount().doubleValue() > 10000) {
            throw new InsufficientBalanceException("Insufficient balance for this transaction.");
        }

        // Validate beneficiary details
        if (request.getBeneficiaryId() == null || request.getBeneficiaryId().length() != 8 || !request.getBeneficiaryId().matches("\\d{8}")) {
            throw new IncorrectBeneficiaryDetailsException("Beneficiary account number is required or not in proper format.");
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