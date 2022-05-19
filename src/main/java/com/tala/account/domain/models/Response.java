package com.tala.account.domain.models;


public enum Response {

    SUCCESS(0, "Success"),
    ACCOUNT_NOT_FOUND(1, "ACCOUNT does not exist by id"),

    EXCEED_MAX_DEPOSIT_AMOUNT_PER_TRANSACTION(2, "Exceeded Maximum Deposit Amount Per Transaction"),
    EXCEED_MAX_DEPOSIT_AMOUNT_PER_DAY(3, "Exceeded Maximum Deposit Amount Per Day"),
    EXCEED_MAX_DEPOSITS(4, "Exceeded Maximum Deposits Per Day"),

    AMOUNT_EXCEED_MAX_WITHDRAWAL_PER_DAY(5, "Exceeded Maximum Withdrawal Amount Per Day"),
    EXCEED_MAX_WITHDRAWAL_PER_TRANSACTION(6, "Exceeded Maximum Withdrawal Amount Per Transaction"),
    EXCEED_MAX_WITHDRAWALS_FOR_TODAY(7, "Exceeded Maximum Withdrawals Per Day"),

    INSUFFICIENT_BALANCE(8, "Amount Exceeds Your Balance"),

    UNKNOWN_TRANSACTION_TYPE(9, "Error Unknown transaction type");

    private Status status;

    Response(final int code, final String message) {
        status = new Status(code, message);
    }

    public Status status() {
        return status;
    }
}
