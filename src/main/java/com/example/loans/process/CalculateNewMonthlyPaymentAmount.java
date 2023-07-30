package com.example.loans.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CalculateNewMonthlyPaymentAmount {

    @JobWorker
    public Map<String, Object> calculateNewMonthlyPaymentAmount(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> processVariables = job.getVariablesAsMap();
        Double referenceInterestRate = (Double) processVariables.get("referenceInterestRate");
        Integer remainingNumberOfPayments = (Integer) processVariables.get("remainingNumberOfPayments");
        Double remainingContractValue = (Double) processVariables.get("remainingContractValue");

        double monthlyInterestRate = (referenceInterestRate / 12) / 100;
        double numerator = remainingContractValue * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, remainingNumberOfPayments);
        double denominator = Math.pow(1 + monthlyInterestRate, remainingNumberOfPayments) - 1;
        double newMonthlyPaymentAmount = numerator / denominator;

        variables.put("newMonthlyPaymentAmount", newMonthlyPaymentAmount);

        return variables;

    }
}
