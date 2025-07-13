package com.onlinebanking.domesticpayments.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DomesticPaymentRequest {
    private String paymentReferenceId;
    private String paymentReference;
    private String beneficiaryId;
    private BigDecimal paymentAmount;
    private String customerId;
    private String payeeAccountNumber;
    private String paymentMethod;
    private String paymentType;
    private LocalDateTime paymentScheduleDate;
    private Integer recurringPaymentDay;
    private String recurringPaymentMonth;
    private LocalDate recurringPaymentEndDate;
    private Integer recurringPaymentNumber;

    public String getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(String paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getPaymentScheduleDate() {
        return paymentScheduleDate;
    }

    public void setPaymentScheduleDate(LocalDateTime paymentScheduleDate) {
        this.paymentScheduleDate = paymentScheduleDate;
    }

    public Integer getRecurringPaymentDay() {
        return recurringPaymentDay;
    }

    public void setRecurringPaymentDay(Integer recurringPaymentDay) {
        this.recurringPaymentDay = recurringPaymentDay;
    }

    public String getRecurringPaymentMonth() {
        return recurringPaymentMonth;
    }

    public void setRecurringPaymentMonth(String recurringPaymentMonth) {
        this.recurringPaymentMonth = recurringPaymentMonth;
    }

    public LocalDate getRecurringPaymentEndDate() {
        return recurringPaymentEndDate;
    }

    public void setRecurringPaymentEndDate(LocalDate recurringPaymentEndDate) {
        this.recurringPaymentEndDate = recurringPaymentEndDate;
    }

    public Integer getRecurringPaymentNumber() {
        return recurringPaymentNumber;
    }

    public void setRecurringPaymentNumber(Integer recurringPaymentNumber) {
        this.recurringPaymentNumber = recurringPaymentNumber;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
}