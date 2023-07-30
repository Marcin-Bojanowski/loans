package com.example.loans.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoansCalculationsService {


    public Map<String, Object> calculateRemainingContractValue(Integer remainingNumberOfPayments, Double financingAmount, Double interestRate, Integer totalNumberOfPayments) {
        Map<String, Object> variables = new HashMap<>();
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

    public Map<String, Object> calculateNewMonthlyPaymentAmount(Double referenceInterestRate, Integer remainingNumberOfPayments, Double remainingContractValue) {
        Map<String, Object> variables = new HashMap<>();
        double monthlyInterestRate = (referenceInterestRate / 12) / 100;
        double numerator = remainingContractValue * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, remainingNumberOfPayments);
        double denominator = Math.pow(1 + monthlyInterestRate, remainingNumberOfPayments) - 1;
        double newMonthlyPaymentAmount = numerator / denominator;
        variables.put("newMonthlyPaymentAmount", newMonthlyPaymentAmount);
        return variables;
    }
}
