package com.tala.account.controllers;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.models.CustomerAccountBalance;
import com.tala.account.domain.models.CustomerAccountState;
import com.tala.account.domain.models.Status;
import com.tala.account.domain.models.TransactionBody;
import com.tala.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accontService;

    @GetMapping
    public ResponseEntity<CustomerAccountState> accounts() {
        return accontService.findAll();
    }

    @GetMapping("balance")
    public ResponseEntity<CustomerAccountBalance> balance(@RequestParam String accountNumber) {
        return accontService.accountBalanceResponse(accountNumber);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/withdraw")
    public ResponseEntity<Status> withdraw(@RequestBody TransactionBody transactionBody) {
        return accontService.transact(TransactionTypes.WITHDRAW, transactionBody.getAccountNumber(), transactionBody.getAmount());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/deposit")
    public ResponseEntity<Status> deposit(@RequestBody TransactionBody transactionBody) {
        return accontService.transact(TransactionTypes.DEPOSIT, transactionBody.getAccountNumber(), transactionBody.getAmount());
    }

}
