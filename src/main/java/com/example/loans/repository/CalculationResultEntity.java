package com.example.loans.repository;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "results")
@Data
@Builder
public class CalculationResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "remaining_contract_value")
    private Double remainingContractValue;

    @Column(name = "new_monthly_payment_amount")
    private Double newMonthlyPaymentAmount;

}
