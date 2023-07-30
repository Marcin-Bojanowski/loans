package com.example.loans.process;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoansCalculationProcessVariables {

    private Integer totalNumberOfPayments;
    private Integer remainingNumberOfPayments;
    private Double monthlyPaymentAmount;
    private Double financingAmount;
    private Double interestRate;
}
