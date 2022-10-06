package org.mnp.loanservice.controller;

import org.mnp.loanservice.model.Loan;
import org.mnp.loanservice.repository.LoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class LoanController {

    private final LoanRepository loanRepository;

    public LoanController(final LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return new ResponseEntity<>(loanRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable(value = "customerId") final int customerId) {
        System.out.println("Invoking Loans Microservice");

        final List<Loan> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customerId);

        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan account) {
        try {
            Loan newAccount = loanRepository.save(account);
            return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
