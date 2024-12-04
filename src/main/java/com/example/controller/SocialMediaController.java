package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

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

    @PostMapping(value = "/login")
    public ResponseEntity<?> processUserLogin(@RequestBody Account account){

        Account verified = verifiedAccount(account);
        if(verified != null){
            return ResponseEntity.status(200).body(verified);
        }
        return ResponseEntity.status(401).body("Username or Password is incorrect");
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        //not blank not over 255, postedBy refers to account id

        if(accountService.getAccountById(message.getPostedBy()) != null){

            if(isValidMessage(message)){
                Message validMessage = messageService.persistMessage(message);
                return ResponseEntity.status(200).body(validMessage);
            }
            
        }
        return ResponseEntity.status(400).body("");
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<?> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping(value = "/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }
    
    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable int messageId){
        if(messageService.deleteMessageById(messageId) == 1){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping(value = "/accounts/{accountId}/messages")
    public ResponseEntity<?> getAllMessagesById(@PathVariable int accountId){
        return ResponseEntity.status(200).body(messageService.getAllMessagesById(accountId));
    }








    //Helpers
    private boolean hasUsername(Account account) {
        return account.getUsername() != null && !account.getUsername().isBlank();
    }

    private boolean usernameIsUnique(String username) {
        return accountService.findAccountByUsername(username).isEmpty();
    }

    private boolean isValidPassword(Account account) {
        return account.getPassword() != null && account.getPassword().length() >= 4;
    }

    private Account verifiedAccount(Account account){
        return accountService.checkAccountCredentials(account.getUsername(), account.getPassword());
    }

    private boolean isValidMessage(Message message){
        return message.getMessageText().length() < 255 && !message.getMessageText().trim().isBlank();
    }
}
