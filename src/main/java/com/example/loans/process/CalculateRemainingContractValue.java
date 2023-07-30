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

        double cumulativePrincipalPaid = cumprinc(interestRate / 12 / 100, totalNumberOfPayments, financingAmount, 1, totalNumberOfPayments - remainingNumberOfPayments, 0);
        double remainingContractValue = financingAmount - cumulativePrincipalPaid;

        variables.put("remainingContractValue", remainingContractValue);


        return variables;

    }

    public static double cumprinc(double annualRate, double nper, double pv, double startPeriod, double endPeriod, int type) {
        if (annualRate <= 0 || nper <= 0 || pv <= 0 || startPeriod > endPeriod) {
            throw new IllegalArgumentException("Nieprawidłowe dane wejściowe");
        }

        double monthlyRate = annualRate / 12;
        double monthlyPayment = calculateMonthlyPayment(monthlyRate, nper, pv);
        double principalPaid = 0;

        for (int period = (int) startPeriod; period <= endPeriod; period++) {
            double interestPaid = calculateInterestPaid(monthlyRate, pv, period, type);
            double principalPayment = monthlyPayment - interestPaid;
            principalPaid += principalPayment;
            pv -= principalPayment;
        }

        return principalPaid;
    }

    private static double calculateMonthlyPayment(double monthlyRate, double nper, double pv) {
        return pv * monthlyRate / (1 - Math.pow(1 + monthlyRate, -nper));
    }

    private static double calculateInterestPaid(double monthlyRate, double pv, int period, int type) {
        double interestFactor = Math.pow(1 + monthlyRate, period - 1);
        return pv * monthlyRate * (1 - interestFactor);
    }
}
