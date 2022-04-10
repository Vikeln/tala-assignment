package com.tala.account.domain.models;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBody {
    @NotNull
    private Double amount;
    @NotNull
    private String accountNumber;
}
