package com.tala.account.controllers;

import com.tala.account.domain.models.CustomerAccountStatementModel;
import com.tala.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("api/account")
public class AccountReportController {

    @Autowired
    private AccountService accountService;

    @GetMapping("statement/report")
    public ResponseEntity<CustomerAccountStatementModel> statement(@RequestParam Date startDate, @RequestParam String accountNumber) {
        return accountService.statement(accountNumber, startDate);
    }

}
