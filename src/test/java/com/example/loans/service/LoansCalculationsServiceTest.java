package com.example.loans.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LoansCalculationsServiceTest {

    private LoansCalculationsService loansCalculationsService;

    @BeforeEach
    public void setUp() {
        loansCalculationsService = new LoansCalculationsService();
    }

    @Test
    public void test1() {
        Map<String, Object> variables = loansCalculationsService.calculateRemainingContractValue(120, 600000D, 6D, 240);
        assertThat(variables).isNotEmpty();
        assertThat(variables.get("remainingContractValue")).isNotNull();
        assertThat(variables.get("remainingContractValue")).isEqualTo(284196.18726009614D);
    }

    @Test
    public void test2() {
        Map<String, Object> variables = loansCalculationsService.calculateNewMonthlyPaymentAmount(6.75D, 120, 284196.18726009614D);
        assertThat(variables).isNotEmpty();
        assertThat(variables.get("newMonthlyPaymentAmount")).isNotNull();
        assertThat(variables.get("newMonthlyPaymentAmount")).isEqualTo(3263.257553951528D);
    }

}