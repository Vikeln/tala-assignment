package com.tala.account.persistence.entities;

import com.tala.account.domain.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer_account_transaction")
public class Transaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    @JoinColumn(name = "customer_account", referencedColumnName = "account_number")
    @ManyToOne(optional = false)
    private CustomerAccount customerAccount;

    @Column(name = "transaction_type", nullable = false)
    private TransactionTypes transactionType;

    @Column(name = "transaction_ref", nullable = false, unique = true)
    private String transactionRef;

    @Column(name = "transaction_status", nullable = false)
    private String status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "amount_transferred", nullable = false)
    private double amountTransferred;

    @Column(name = "account_opening_balance")
    private Double accountOpeningBalance;

    @Column(name = "account_closing_balance")
    private Double accountClosingBalance;
}
