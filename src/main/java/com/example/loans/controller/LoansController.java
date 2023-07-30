package com.example.loans.controller;

import com.example.loans.service.LoansFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoansController {


    private final LoansFacade loansFacade;

    @PostMapping("/loans")
    public ResponseEntity<String> startCalculateLoans(@Valid @RequestBody LoansDTO loansDTO) {
        String reference = loansFacade.startCalculateLoans(loansDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Started process instance " + reference);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
