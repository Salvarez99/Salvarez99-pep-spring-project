package com.example.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account persistAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElse(null);
    }
    
    public Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account checkAccountCredentials(String username, String password){

        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(username, password);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        return null;
    }
}

