package com.onlinebanking.domesticpayments.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentDeclinedException.class)
    public ResponseEntity<ApiError> handlePaymentDeclined(PaymentDeclinedException ex) {
        ApiError error = new ApiError();
        error.setType("https://onlinebanking.com/probs/payment-declined");
        error.setTitle("Payment Declined");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDetail(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handler for InsufficientBalanceException
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiError> handleInsufficientBalance(InsufficientBalanceException ex) {
        ApiError error = new ApiError();
        error.setType("https://onlinebanking.com/probs/insufficient-balance");
        error.setTitle("Insufficient Balance");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDetail(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handler for IncorrectBeneficiaryDetailsException
    @ExceptionHandler(IncorrectBeneficiaryDetailsException.class)
    public ResponseEntity<ApiError> handleIncorrectBeneficiaryDetails(IncorrectBeneficiaryDetailsException ex) {
        ApiError error = new ApiError();
        error.setType("https://onlinebanking.com/probs/incorrect-beneficiary-details");
        error.setTitle("Incorrect Beneficiary Details");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDetail(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handler for TechnicalErrorException
    @ExceptionHandler(TechnicalErrorException.class)
    public ResponseEntity<ApiError> handleTechnicalError(TechnicalErrorException ex) {
        ApiError error = new ApiError();
        error.setType("https://onlinebanking.com/probs/technical-error");
        error.setTitle("Technical Error");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setDetail(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}