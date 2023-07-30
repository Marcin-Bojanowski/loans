package com.example.loans.process;

import com.example.loans.CalculationResultEntity;
import com.example.loans.LoansFacade;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CalculationsSave {

    private final LoansFacade loansFacade;

    @JobWorker
    @Transactional
    public void calculationsSave(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> processVariables = job.getVariablesAsMap();
        Double newMonthlyPaymentAmount = (Double) processVariables.get("newMonthlyPaymentAmount");
        Double remainingContractValue = (Double) processVariables.get("remainingContractValue");
        CalculationResultEntity result = CalculationResultEntity.builder()
                .newMonthlyPaymentAmount(newMonthlyPaymentAmount)
                .remainingContractValue(remainingContractValue).build();
        loansFacade.saveResult(result);

    }
}
