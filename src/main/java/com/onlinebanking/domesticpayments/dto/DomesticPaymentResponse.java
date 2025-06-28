package com.onlinebanking.domesticpayments.dto;

public class DomesticPaymentResponse {
    private Long paymentReferenceId;
    private String paymentStatus;
    private String paymentStatusReason;

    // Getters and setters

    public Long getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(Long paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatusReason() {
        return paymentStatusReason;
    }

    public void setPaymentStatusReason(String paymentStatusReason) {
        this.paymentStatusReason = paymentStatusReason;
    }
}