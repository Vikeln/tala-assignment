package com.tala.account.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountStatementModel {
    private CustomerAccountState customerAccountState;
    private Collection<TransactionModel> transactions;
}
