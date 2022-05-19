package com.tala.account.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountState {
    private String accountNumber;

    private double balance;

    private Integer depositToday;

    private double totalDepositToday;

    private Integer withdrawalsToday;

    private double totalWithdrawalsToday;

}
