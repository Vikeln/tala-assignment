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

    @PostMapping("withdraw")
    public ResponseEntity withdraw(@RequestBody TransactionBody transactionBody , HttpServletRequest request){
        return accontService.transact(TransactionTypes.WITHDRAW,transactionBody.getAccountNumber(), transactionBody.getAmount(), request);
    }

    @PostMapping("deposit")
    public ResponseEntity deposit(@RequestBody TransactionBody transactionBody, HttpServletRequest request){
        return accontService.transact(TransactionTypes.DEPOSIT,transactionBody.getAccountNumber(), transactionBody.getAmount(), request);
    }
}
