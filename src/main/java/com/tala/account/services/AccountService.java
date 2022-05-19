package com.tala.account.services;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.models.CustomerAccountBalance;
import com.tala.account.domain.models.CustomerAccountState;
import com.tala.account.domain.models.CustomerAccountStatementModel;
import com.tala.account.domain.models.Status;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface AccountService {
    ResponseEntity<Status> transact(TransactionTypes transactionType, String accountNumber, double amount);

    ResponseEntity<CustomerAccountBalance> accountBalanceResponse(String byId);

    ResponseEntity<CustomerAccountState> findAll();

    ResponseEntity<CustomerAccountStatementModel> statement(String accountNumber, Date startDate);
}
