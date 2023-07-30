package com.example.loans.service;

import com.example.loans.controller.LoansDTO;
import com.example.loans.process.LoansCalculationProcessVariables;
import com.example.loans.repository.CalculationResultEntity;
import com.example.loans.repository.CalculationResultRepository;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoansService {

    private final ZeebeClient zeebeClient;
    private final CalculationResultRepository calculationResultRepository;

    public String startCalculateLoans(LoansDTO loansDTO) {
        LoansCalculationProcessVariables processVariables =
                LoansCalculationProcessVariables
                        .builder()
                        .totalNumberOfPayments(loansDTO.getTotalNumberOfPayments())
                        .remainingNumberOfPayments(loansDTO.getRemainingNumberOfPayments())
                        .monthlyPaymentAmount(loansDTO.getMonthlyPaymentAmount())
                        .financingAmount(loansDTO.getFinancingAmount())
                        .interestRate(loansDTO.getInterestRate())
                        .build();

        ProcessInstanceEvent processInstance =
                zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId("LoansCalculation")
                        .latestVersion()
                        .variables(processVariables)
                        .send()
                        .join(); // blocking call!

        return String.valueOf(processInstance.getProcessInstanceKey());
    }

    public void saveResult(CalculationResultEntity result) {
        calculationResultRepository.save(result);
    }
}
