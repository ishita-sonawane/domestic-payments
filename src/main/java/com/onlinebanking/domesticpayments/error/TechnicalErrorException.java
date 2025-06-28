package com.onlinebanking.domesticpayments.error;

public class TechnicalErrorException extends RuntimeException {
    public TechnicalErrorException(String message) {
        super(message);
    }
}