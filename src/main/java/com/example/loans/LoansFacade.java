package com.example.loans;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoansFacade {

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
