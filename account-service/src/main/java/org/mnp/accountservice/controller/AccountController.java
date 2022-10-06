package org.mnp.accountservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.mnp.accountservice.config.AccountsConfiguration;
import org.mnp.accountservice.model.*;
import org.mnp.accountservice.repository.AccountRepository;
import org.mnp.accountservice.service.client.CustomerDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository accountRepository;

    private final AccountsConfiguration accountsConfiguration;

    private final CustomerDetailsService customerDetailsService;

    public AccountController(final AccountRepository accountRepository, final AccountsConfiguration accountsConfiguration, final CustomerDetailsService customerDetailsService) {
        this.accountRepository = accountRepository;
        this.accountsConfiguration = accountsConfiguration;
        this.customerDetailsService = customerDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Account> getByCustomerIdAccounts(@PathVariable(value = "customerId") final int customerId) {
        final Account account = accountRepository.findByCustomerId(customerId);

        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account newAccount = accountRepository.save(account);
            return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details/{customerId}")
    //@CircuitBreaker(name = "detailsForCustomerSupportApp")
    @Retry(name = "retryOnCustomerDetails", fallbackMethod = "getCustomerDetailsFallback")
    public ResponseEntity<CustomerDetails> getCustomerDetails(@PathVariable(value = "customerId") final int customerId) {
        final Account account = accountRepository.findByCustomerId(customerId);

        if (account != null) {
            final List<Loan> customerLoans = customerDetailsService.getCustomerLoans(customerId);
            final List<Card> customerCards = customerDetailsService.getCustomerCards(customerId);

            final CustomerDetails customerDetails = CustomerDetails.of(account, customerLoans, customerCards);
            return new ResponseEntity<>(customerDetails, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<CustomerDetails> getCustomerDetailsFallback(final int customerId, final Throwable t) {
        final Account account = accountRepository.findByCustomerId(customerId);

        if (account != null) {
            final List<Loan> customerLoans = customerDetailsService.getCustomerLoans(customerId);

            final CustomerDetails customerDetails = CustomerDetails.of(account, customerLoans, List.of());
            return new ResponseEntity<>(customerDetails, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
