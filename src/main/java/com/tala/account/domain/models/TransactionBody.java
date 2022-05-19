package com.tala.account.domain.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBody {
    @NotNull
    @Min(value = 10, message = "Minimum allowed transaction amount is 10")
    private Double amount;

    @NotNull
    @NotEmpty
    private String accountNumber;
}
