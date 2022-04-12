package com.tala.account.domain.models;


public enum Response {

    SUCCESS(0, "Success"),
    ACCOUNT_NOT_FOUND(1, "ACCOUNT does not exist by id"),
    ACCOUNT_NOT_SAVED(2, "Error saving ACCOUNT data"),

    EXCEED_MAX_DEP_AMOUNT(3, "Exceeded Maximum Deposit Per Transaction"),
    EXCEED_MAX_DEPOSIT_TODAYS(4, "Exceeded Maximum Deposit Per Day"),
    EXCEED_MAX_DEPOSITS(5, "This will Exceed Your Maximum Deposit Per Day"),

    AMOUNT_EXCEED_MAX_WITHDRAWAL(6, "Exceeded Maximum Withdrawal Amount"),
    EXCEED_MAX_WITHDRAWAL_PER_TRANSACTION(7, "Exceeded Maximum Withdrawal Per Transaction"),
    EXCEED_MAX_WITHDRAWALS_FOR_TODAY(8, "Exceeded Maximum Withdrawals Per Day"),
    INSUFFICIENT_BALANCE(8, "Amount Exceeds Your Balance"),

    UNKNOWN_TRANSACTION_TYPE(9, "Error Unknown transaction type"),
    CUSTOM(12, "{0}"),
    VALIDATION_FAILURE(13, "Field validation failed");

    private Status status;

    Response(final int code, final String message) {
        status = new Status(code, message);
    }

    public Status status(){
        return status;
    }
}
