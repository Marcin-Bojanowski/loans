package com.example.loans.service;

import com.example.loans.controller.LoansDTO;
import com.example.loans.repository.CalculationResultEntity;
import com.example.loans.repository.CalculationResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoansFacade {

    private final LoansService loansService;

    private final CalculationResultRepository calculationResultRepository;

    public String startCalculateLoans(LoansDTO loansDTO) {
        return loansService.startCalculateLoans(loansDTO);

    }

    public void saveResult(CalculationResultEntity result) {
        loansService.saveResult(result);
    }
}
