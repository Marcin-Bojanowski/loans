package com.example.loans.service;

import com.example.loans.controller.LoansDTO;
import com.example.loans.repository.CalculationResultEntity;
import com.example.loans.repository.CalculationResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoansFacade {

    private final LoansService loansService;
    private final LoansCalculationsService loansCalculationsService;

    private final CalculationResultRepository calculationResultRepository;

    public String startCalculateLoans(LoansDTO loansDTO) {
        return loansService.startCalculateLoans(loansDTO);

    }

    public void saveResult(CalculationResultEntity result) {
        loansService.saveResult(result);
    }

    public Map<String, Object> calculateRemainingContractValue(Integer remainingNumberOfPayments, Double financingAmount, Double interestRate, Integer totalNumberOfPayments) {
        return loansCalculationsService.calculateRemainingContractValue(remainingNumberOfPayments, financingAmount, interestRate, totalNumberOfPayments);
    }

    public Map<String, Object> calculateNewMonthlyPaymentAmount(Double referenceInterestRate, Integer remainingNumberOfPayments, Double remainingContractValue) {
        return loansCalculationsService.calculateNewMonthlyPaymentAmount(referenceInterestRate, remainingNumberOfPayments, remainingContractValue);
    }
}
