package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        if (!hasUsername(account) || !isValidPassword(account)) {
            return ResponseEntity.status(400).body("Username is blank or password is less than 4 characters long");
        }

        if (!usernameIsUnique(account.getUsername())) {
            return ResponseEntity.status(409).body("Username is not unique!");
        }

        Account savedAccount = accountService.persistAccount(account);
        return ResponseEntity.status(200).body(savedAccount);
    }

    private boolean hasUsername(Account account) {
        return account.getUsername() != null && !account.getUsername().isBlank();
    }

    private boolean usernameIsUnique(String username) {
        return accountService.findAccountByUsername(username).isEmpty();
    }

    private boolean isValidPassword(Account account) {
        return account.getPassword() != null && account.getPassword().length() >= 4;
    }
}
