package com.example.loans.controller;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoansDTO {

    @NotNull
    private Integer totalNumberOfPayments;
    @NotNull
    private Integer remainingNumberOfPayments;
    @NotNull
    private Double monthlyPaymentAmount;
    @NotNull
    private Double financingAmount;
    @NotNull
    private Double interestRate;
}
