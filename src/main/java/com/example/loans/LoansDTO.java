package com.example.loans;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

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
