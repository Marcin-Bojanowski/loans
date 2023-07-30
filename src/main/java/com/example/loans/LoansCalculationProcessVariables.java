package com.example.loans;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LoansCalculationProcessVariables {

    private Integer totalNumberOfPayments;
    private Integer remainingNumberOfPayments;
    private Double monthlyPaymentAmount;
    private Double financingAmount;
    private Double interestRate;
}
