package com.tala.account.controllers;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.models.Status;
import com.tala.account.domain.models.TransactionBody;
import com.tala.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accontService;

    @GetMapping
    public ResponseEntity accounts() {
        return accontService.findAll();
    }


    @GetMapping("balance")
    @Cacheable(cacheNames = "accountCache", key = "#a0")
    public ResponseEntity balance(@RequestParam String accountNumber) {
        return accontService.accountBalanceResponse(accountNumber);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{accountNumber}/withdraw")
    public Status withdraw(@PathVariable String accountNumber, @RequestBody TransactionBody transactionBody) {
        return accontService.transact(TransactionTypes.WITHDRAW, accountNumber, transactionBody.getAmount());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{accountNumber}/deposit")
    public Status deposit(@PathVariable String accountNumber, @RequestBody TransactionBody transactionBody) {
        return accontService.transact(TransactionTypes.DEPOSIT, accountNumber, transactionBody.getAmount());
    }

}
