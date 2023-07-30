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
public class CalculateRemainingContractValue {

    private final LoansFacade loansFacade;

    @JobWorker
    public Map<String, Object> calculateRemainingContractValue(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> processVariables = job.getVariablesAsMap();

        Integer remainingNumberOfPayments = (Integer) processVariables.get("remainingNumberOfPayments");
        Double financingAmount = (Double) processVariables.get("financingAmount");
        Double interestRate = (Double) processVariables.get("interestRate");
        Integer totalNumberOfPayments = (Integer) processVariables.get("totalNumberOfPayments");

        return loansFacade.calculateRemainingContractValue(remainingNumberOfPayments, financingAmount, interestRate, totalNumberOfPayments);

    }

}
