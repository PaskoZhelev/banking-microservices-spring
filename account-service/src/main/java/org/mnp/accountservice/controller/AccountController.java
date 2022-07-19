package org.mnp.accountservice.controller;

import org.mnp.accountservice.model.Account;
import org.mnp.accountservice.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
