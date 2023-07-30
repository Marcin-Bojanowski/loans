package com.example.loans.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CalculateRemainingContractValue {

    @JobWorker
    public Map<String, Object> calculateRemainingContractValue(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> processVariables = job.getVariablesAsMap();
        Integer remainingNumberOfPayments = (Integer) processVariables.get("remainingNumberOfPayments");
        Double monthlyPaymentAmount = (Double) processVariables.get("monthlyPaymentAmount");
        Double financingAmount = (Double) processVariables.get("financingAmount");
        Double interestRate = (Double) processVariables.get("interestRate");
        Integer totalNumberOfPayments = (Integer) processVariables.get("totalNumberOfPayments");

        double monthlyInterestRate = interestRate / 12.0 / 100.0;
        double numerator = financingAmount * Math.pow(1 + monthlyInterestRate, totalNumberOfPayments - remainingNumberOfPayments);
        double denominator = Math.pow(1 + monthlyInterestRate, totalNumberOfPayments) - 1;
        double remainingContractValue = numerator - (monthlyPaymentAmount * denominator / monthlyInterestRate);

        variables.put("remainingContractValue", remainingContractValue);

        return variables;

    }
}
