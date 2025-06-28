package com.onlinebanking.domesticpayments.repository;

import com.onlinebanking.domesticpayments.model.DomesticPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticPaymentRepository extends JpaRepository<DomesticPayment, Long> {
}