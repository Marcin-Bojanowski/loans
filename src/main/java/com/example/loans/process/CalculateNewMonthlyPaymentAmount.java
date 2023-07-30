package com.example.loans.process;

import com.example.loans.service.LoansFacade;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CalculateNewMonthlyPaymentAmount {

    private final LoansFacade loansFacade;

    @JobWorker
    public Map<String, Object> calculateNewMonthlyPaymentAmount(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> processVariables = job.getVariablesAsMap();

        Double referenceInterestRate = (Double) processVariables.get("referenceInterestRate");
        Integer remainingNumberOfPayments = (Integer) processVariables.get("remainingNumberOfPayments");
        Double remainingContractValue = (Double) processVariables.get("remainingContractValue");

        return loansFacade.calculateNewMonthlyPaymentAmount(referenceInterestRate, remainingNumberOfPayments, remainingContractValue);
    }
}
