package com.tala.account.utils;

import org.springframework.beans.factory.annotation.Value;

public class TransactionConstraints {

    @Value("${default.maxDepositAmount}")
    private double maxDepositAmount;

    @Value("${default.max.account.deposit.frequency}")
    private Integer defaultAccountDepositTransactions;

    @Value("${default.account.depositamount}")
    private double defaultAccountDepositAmount;

    @Value("${default.account.withdrawals.amount.max}")
    private double defaultAccountWithdrawalsMaxAmount;

    @Value("${default.account.withdrawals.transaction.max}")
    private double defaultAccountWithdrawalsToday;

    @Value("${default.max.account.withdrawals.frequency}")
    private double defaultAccountWithdrawalsFrequency;

    public double getMaxDepositAmount() {
        return maxDepositAmount;
    }

    public Integer getDefaultAccountDepositTransactions() {
        return defaultAccountDepositTransactions;
    }

    public double getDefaultAccountDepositAmount() {
        return defaultAccountDepositAmount;
    }

    public double getDefaultAccountWithdrawalsMaxAmount() {
        return defaultAccountWithdrawalsMaxAmount;
    }

    public double getDefaultAccountWithdrawalsToday() {
        return defaultAccountWithdrawalsToday;
    }

    public double getDefaultAccountWithdrawalsFrequency() {
        return defaultAccountWithdrawalsFrequency;
    }
}
