package com.tala.account.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tala.account.domain.TransactionTypes;
import com.tala.account.persistence.entities.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionModel extends Auditable {
    private Integer id;

    private TransactionTypes transactionType;

    private String transactionRef;

    private String status;

    private String comments;

    private double amount;

    private Double accountOpeningBalance;

    private Double accountClosingBalance;
}
