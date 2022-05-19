package com.tala.account.domain.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBody {
    @NotNull
    private Double amount;
    @NotNull
    private String accountNumber;
}
