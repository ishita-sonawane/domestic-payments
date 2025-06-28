package com.onlinebanking.domesticpayments.service;

import com.onlinebanking.domesticpayments.dto.DomesticPaymentRequest;
import com.onlinebanking.domesticpayments.model.DomesticPayment;
import com.onlinebanking.domesticpayments.repository.DomesticPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DomesticPaymentService {

    @Autowired
    private DomesticPaymentRepository repository;

    public DomesticPayment savePayment(DomesticPaymentRequest request) {
        DomesticPayment payment = new DomesticPayment();
       // payment.setPaymentReferenceID(UUID.randomUUID().toString());
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