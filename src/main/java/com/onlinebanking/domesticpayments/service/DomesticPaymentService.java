package com.onlinebanking.domesticpayments.service;

import com.onlinebanking.domesticpayments.dto.DomesticPaymentRequest;
import com.onlinebanking.domesticpayments.model.DomesticPayment;
import com.onlinebanking.domesticpayments.repository.DomesticPaymentRepository;
import org.springframework.stereotype.Service;


@Service
public class DomesticPaymentService {

    private final DomesticPaymentRepository repository;

    public DomesticPaymentService(DomesticPaymentRepository repository) {
        this.repository = repository;
    }

    public DomesticPayment savePayment(DomesticPaymentRequest request) {
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