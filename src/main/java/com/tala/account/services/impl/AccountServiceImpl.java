package com.tala.account.services.impl;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.mappers.DomainMapper;
import com.tala.account.domain.models.CustomerAccountState;
import com.tala.account.domain.models.CustomerAccountStatementModel;
import com.tala.account.domain.models.Response;
import com.tala.account.domain.models.Status;
import com.tala.account.persistence.entities.CustomerAccount;
import com.tala.account.persistence.entities.Transaction;
import com.tala.account.persistence.repos.AccountRepo;
import com.tala.account.persistence.repos.TransactionRepo;
import com.tala.account.services.AccountService;
import com.tala.account.utils.CacheConfig;
import com.tala.account.utils.TransactionConstraints;
import com.tala.account.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class AccountServiceImpl extends ResponseInterfaceImpl implements AccountService {
    private DomainMapper mapper = Mappers.getMapper(DomainMapper.class);

    @Autowired
    private CacheConfig cacheConfig;

    @Autowired
    private TransactionConstraints constraints;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public ResponseEntity<Status> transact(TransactionTypes transactionType, String accountNumber, double amount) {

        log.info(transactionType.name().concat(" attempt on account => ").concat(accountNumber).concat(" of amount ").concat(String.valueOf(amount)));

        Optional<CustomerAccount> customerAccount = accountRepo.findById(accountNumber);
        Status status;
        if (customerAccount.isPresent()) {
            status = initialTransactionChecks(customerAccount.get(), transactionType, amount);
            if (status.getCode() == Response.SUCCESS.status().getCode()) {
                status = transact(customerAccount.get(), transactionType, amount);
            } else {
                Transaction transaction = createTransaction(customerAccount.get(), amount, customerAccount.get().getBalance(), customerAccount.get().getBalance(), transactionType, CacheConfig.FAILED, status.getMessage());
                log.info("created failed transaction {}", transaction);
            }
        } else
            status = Response.ACCOUNT_NOT_FOUND.status();

        return statusResponse(status);
    }

    @Override
    public ResponseEntity accountBalanceResponse(String accountNumber) {
        Optional<CustomerAccount> account = accountRepo.findById(accountNumber);
        if (account.isPresent()) {

            Transaction transaction = createTransaction(account.get(), 0.0, account.get().getBalance(), account.get().getBalance(), TransactionTypes.BALANCE_QUERY, CacheConfig.SUCCESS, null);
            log.info("created BALANCE_QUERY transaction {}", transaction);

            return ResponseEntity.ok(mapper.formatCustomerAccount(cacheConfig.getCustomerAccountState(account.get())));
        }
        return ResponseEntity.badRequest().body(Response.ACCOUNT_NOT_FOUND.status());
    }

    @Override
    public ResponseEntity findAll() {
        return ResponseEntity.ok(accountRepo.findAll().stream().map(ac -> cacheConfig.getCustomerAccountState(ac)));
    }

    @Override
    public ResponseEntity statement(String accountNumber, Date startDate) {
        Optional<CustomerAccount> account = accountRepo.findById(accountNumber);
        if (account.isPresent())
            return ResponseEntity.ok(
                    new CustomerAccountStatementModel(cacheConfig.getCustomerAccountState(account.get()),
                            transactionRepo.findAllByCustomerAccountAndDateTimeCreatedIsAfter(account.get(), startDate).stream().map(transaction -> mapper.formatTransactionModelFromTransaction(transaction)).collect(Collectors.toList())));
        return ResponseEntity.badRequest().body(Response.ACCOUNT_NOT_FOUND.status());

    }

    public Status transact(CustomerAccount account, TransactionTypes transactionType, double amount) {

        double openingBalance = account.getBalance();
        double closingBalance;

        if (transactionType.equals(TransactionTypes.DEPOSIT)) {
            closingBalance = openingBalance + amount;
            account.setBalance(closingBalance);
        } else {
            closingBalance = openingBalance - amount;
            account.setBalance(closingBalance);
        }

        account.setLastTransaction(new Date());
        account = accountRepo.save(account);

        Transaction transaction = createTransaction(account, amount, openingBalance, closingBalance, transactionType, CacheConfig.SUCCESS, null);

        cacheConfig.refreshAccountStateInCache(account);
        log.info("Created transaction {}", transaction);

        return Response.SUCCESS.status();
    }

    private Transaction createTransaction(CustomerAccount account, double amount, double openingBalance, double closingBalance, TransactionTypes transactionType, String status, String comments) {
        String ref = Util.createTransactionRef(transactionRepo);

        Transaction transaction = new Transaction();
        transaction.setCustomerAccount(account);
        transaction.setTransactionRef(ref);
        transaction.setAmountTransferred(amount);
        transaction.setTransactionType(transactionType);
        transaction.setAccountClosingBalance(closingBalance);
        transaction.setAccountOpeningBalance(openingBalance);


        transaction.setStatus(status);
        transaction.setComments(comments);
        return transactionRepo.save(transaction);
    }

    public Status initialTransactionChecks(CustomerAccount account, TransactionTypes transactionType, double amount) {
        CustomerAccountState accountState = cacheConfig.getCustomerAccountState(account);
        if (transactionType.name().equalsIgnoreCase(TransactionTypes.DEPOSIT.name())) {
//            check max deposti amount
            if (amount > constraints.getMaxDepositAmount())
                return Response.EXCEED_MAX_DEPOSIT_AMOUNT_PER_TRANSACTION.status();
//            check max deposit times
            else if (accountState.getDepositToday().equals(constraints.getDefaultAccountDepositTransactions()))
                return Response.EXCEED_MAX_DEPOSITS.status();
//            check max deposti amount today
            else if ((accountState.getTotalDepositToday() + amount) > constraints.getDefaultAccountDepositAmount())
                return Response.EXCEED_MAX_DEPOSIT_AMOUNT_PER_DAY.status();
        }

//        else WITHDRAW
        else if (transactionType.name().equalsIgnoreCase(TransactionTypes.WITHDRAW.name())) {
//              ■ Cannot withdraw when balance is less than withdrawal amount

//            max withdrawal for the day = $50K
            if ((accountState.getTotalWithdrawalsToday() + amount) > constraints.getDefaultAccountWithdrawalsMaxAmount())
                return Response.AMOUNT_EXCEED_MAX_WITHDRAWAL_PER_DAY.status();
//              ■ Max withdrawal frequency = 3 transactions/day
            else if (accountState.getWithdrawalsToday() >= constraints.getDefaultAccountWithdrawalsFrequency())
                return Response.EXCEED_MAX_WITHDRAWALS_FOR_TODAY.status();
//              ■ Max withdrawal per transaction = $20K
            else if (amount > constraints.getDefaultAccountWithdrawalsToday())
                return Response.EXCEED_MAX_WITHDRAWAL_PER_TRANSACTION.status();
            else if (amount > account.getBalance())
                return Response.INSUFFICIENT_BALANCE.status();

        } else {
            return Response.UNKNOWN_TRANSACTION_TYPE.status();
        }
        return Response.SUCCESS.status();
    }


}
