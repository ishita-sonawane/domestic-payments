package com.onlinebanking.domesticpayments.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "domestic_payments", schema = "online_banking")
public class DomesticPayment {
    public Long getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(Long paymentReferenceId) {
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatusReason() {
        return paymentStatusReason;
    }

    public void setPaymentStatusReason(String paymentStatusReason) {
        this.paymentStatusReason = paymentStatusReason;
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

    @Id
    @Column(name = "payment_reference_id", length = 9)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentReferenceId;

    @Column(name = "beneficiary_id", length = 8, nullable = false)
    private String beneficiaryId;

    @Column(name = "payment_amount", precision = 11, scale = 2, nullable = false)
    private BigDecimal paymentAmount;

    @Column(name = "customer_id", length = 10, nullable = false)
    private String customerId;

    @Column(name = "payee_account_number", length = 9, nullable = false)
    private String payeeAccountNumber;

    @Column(name = "payment_status", length = 10)
    private String paymentStatus;

    @Column(name = "payment_method", length = 20, nullable = false)
    private String paymentMethod;

    @Column(name = "payment_status_reason", length = 50)
    private String paymentStatusReason;

    @Column(name = "payment_type", length = 15)
    private String paymentType;

    @Column(name = "payment_schedule_date")
    private LocalDateTime paymentScheduleDate;

    @Column(name = "recurring_payment_day")
    private Integer recurringPaymentDay;

    @Column(name = "recurring_payment_month", length = 15)
    private String recurringPaymentMonth;

    @Column(name = "recurring_payment_end_date")
    private LocalDate recurringPaymentEndDate;

    @Column(name = "recurring_payment_number")
    private Integer recurringPaymentNumber;

    // Getters and setters for all fields
    // ...
}