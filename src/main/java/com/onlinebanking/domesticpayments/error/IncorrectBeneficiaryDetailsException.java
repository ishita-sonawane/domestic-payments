package com.onlinebanking.domesticpayments.error;

public class IncorrectBeneficiaryDetailsException extends RuntimeException {
    public IncorrectBeneficiaryDetailsException(String message) {
        super(message);
    }
}