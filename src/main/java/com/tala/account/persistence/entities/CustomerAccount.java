package com.tala.account.persistence.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer_account")
public class CustomerAccount extends Auditable {

    @Id
    @GeneratedValue(generator = "account_number_generator")
    @GenericGenerator(
            name = "account_number_generator",
            strategy = "com.tala.account.persistence.generators.AccountNumberGenerator"
    )
    @Column(name = "account_number", nullable = false, length = 255, updatable = false)
    private String accountNumber;

    private double balance;

    private Integer transactionsToday;

    private Integer depositToday;

    private double totalDepositToday;

    private Integer withdrawalsToday;

    private double totalWithdrawalsToday;

}