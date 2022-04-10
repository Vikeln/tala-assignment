package com.tala.account.domain.models;


public enum Response {

    SUCCESS(0, "Success"),
    ACCOUNT_NOT_FOUND(1, "ACCOUNT does not exist by id"),
    ACCOUNT_NOT_SAVED(2, "Error saving ACCOUNT data"),
    EXCEED_MAX_DEP_AMOUNT(3, "Error You have exceeded your maximum deposit amount for the day"),
    EXCEED_MAX_DEPOSITS(4, "Error You have exceeded the maximum deposit for the day"),
    EXCEED_MAX_WITHDRAWALS(5, "Error You have exceeded the maximum withdrawal for the day"),
    EXCEED_MAX_WITHDRAWALS_TODAY(6, "Error You have exceeded the maximum withdrawal for the day"),
    EXCEED_MAX_WITHDRAWALS_TOTAL_TODAY(7, "Error You have exceeded the maximum withdrawal times for the day"),
    UNKNOWN_TRANSACTION_TYPE(8, "Error Unknown transaction type"),
    MAX_DEPOSIT_AMOUNT(9, "Error This is the maximum deposit amount for the day"),
    NOT_FOUND(10, "ACCOUNT could not be found by supplied id"),
    CUSTOM(11, "{0}"),
    VALIDATION_FAILURE(12, "Field validation failed");

    private Status status;
    Response(final int code, final String message) {
        status = new Status(code,message);
    }

    public Status status(){
        return status;
    }
}
