package com.tala.account.services;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.models.Status;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    Status transact(TransactionTypes transactionType, String accountNumber, double amount);

    ResponseEntity accountBalanceResponse(String byId);

    ResponseEntity findAll();
}
