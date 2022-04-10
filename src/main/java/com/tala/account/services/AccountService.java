package com.tala.account.services;

import com.tala.account.domain.TransactionTypes;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {
    ResponseEntity transact(TransactionTypes transactionType, String accountNumber, double amount, HttpServletRequest request);

    ResponseEntity accountBalanceResponse(String byId);
}
