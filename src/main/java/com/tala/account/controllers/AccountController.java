package com.tala.account.controllers;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.models.TransactionBody;
import com.tala.account.services.AccountService;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accontService;

    @GetMapping("balance")
    @Cacheable(cacheNames = "accountCache",key = "#a0")
    public ResponseEntity balance(@RequestParam String accountNumber, HttpServletRequest request){
        return accontService.accountBalanceResponse(accountNumber);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity withdraw(@PathVariable String accountNumber,@RequestBody TransactionBody transactionBody , HttpServletRequest request){
        return accontService.transact(TransactionTypes.WITHDRAW,accountNumber, transactionBody.getAmount(), request);
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity deposit(@PathVariable String accountNumber, @RequestBody TransactionBody transactionBody, HttpServletRequest request){
        return accontService.transact(TransactionTypes.DEPOSIT,accountNumber, transactionBody.getAmount(), request);
    }

}
