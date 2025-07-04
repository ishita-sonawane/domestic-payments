package com.onlinebanking.domesticpayments.service;

import com.onlinebanking.domesticpayments.dto.DomesticPaymentRequest;
import com.onlinebanking.domesticpayments.model.DomesticPayment;
import com.onlinebanking.domesticpayments.repository.DomesticPaymentRepository;
import com.onlinebanking.domesticpayments.security.JwtRequestFilter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class DomesticPaymentService {
    Logger logger = org.slf4j.LoggerFactory.getLogger(DomesticPaymentService.class);

    private final DomesticPaymentRepository repository;

    public DomesticPaymentService(DomesticPaymentRepository repository) {
        this.repository = repository;
    }

    public DomesticPayment savePayment(DomesticPaymentRequest request) {
        logger.info("DomesticPaymentService: savePayment called");
        DomesticPayment payment = new DomesticPayment();
        payment.setBeneficiaryId(request.getBeneficiaryId());
        payment.setPaymentAmount(request.getPaymentAmount());
        payment.setPaymentType(request.getPaymentType());
        payment.setPaymentScheduleDate(request.getPaymentScheduleDate());
        payment.setCustomerId(request.getCustomerId());
        payment.setPayeeAccountNumber(request.getPayeeAccountNumber());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus("PENDING"); // Default status

        return repository.save(payment);
    }
}