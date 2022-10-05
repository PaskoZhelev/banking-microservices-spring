package org.mnp.accountservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.mnp.accountservice.config.AccountsConfiguration;
import org.mnp.accountservice.model.Account;
import org.mnp.accountservice.repository.AccountRepository;
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

    public AccountController(AccountRepository accountRepository, AccountsConfiguration accountsConfiguration) {
        this.accountRepository = accountRepository;
        this.accountsConfiguration = accountsConfiguration;
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

}
