package com.tala.account.persistence.repos;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.persistence.entities.CustomerAccount;
import com.tala.account.persistence.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findDistinctByTransactionRef(String ref);

    Collection<Transaction> findAllByCustomerAccountAndDateTimeCreatedIsAfter(CustomerAccount customerAccount, Date end);

    Integer countAllByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccountAndStatus(Date startToday, TransactionTypes transactionType, CustomerAccount account, String status);

    @Query("SELECT SUM(t.amountTransferred) from customer_account_transaction t WHERE t.transactionType = :transactionType and t.dateTimeCreated >= :startToday and t.customerAccount = :account and t.status = :status")
    Double sumTransactionsByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccount(@Param("startToday") Date startToday, @Param("transactionType") TransactionTypes transactionType, @Param("account") CustomerAccount account, @Param("status") String status);
}
