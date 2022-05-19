package com.tala.account.utils;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.models.CustomerAccountState;
import com.tala.account.persistence.entities.CustomerAccount;
import com.tala.account.persistence.repos.TransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class CacheConfig {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    @Autowired
    CacheManager cacheManager;
    @Autowired
    private TransactionRepo transactionRepo;

    @Cacheable(cacheNames = "accountStateCache", key = "#account.accountNumber")
    public CustomerAccountState getCustomerAccountState(CustomerAccount account) {
        Date startToday = DateUtil.atStartOfDay(new Date());
        return new CustomerAccountState(
                account.getAccountNumber(),
                account.getBalance(),
                Optional.ofNullable(transactionRepo.countAllByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccountAndStatus(startToday, TransactionTypes.DEPOSIT, account, SUCCESS)).orElse(0),
                Optional.ofNullable(transactionRepo.sumTransactionsByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccount(startToday, TransactionTypes.DEPOSIT, account, SUCCESS)).orElse(0.0),
                Optional.ofNullable(transactionRepo.countAllByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccountAndStatus(startToday, TransactionTypes.WITHDRAW, account, SUCCESS)).orElse(0),
                Optional.ofNullable(transactionRepo.sumTransactionsByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccount(startToday, TransactionTypes.WITHDRAW, account, SUCCESS)).orElse(0.0));
    }

    @CachePut(value = "accountStateCache", key = "#account.accountNumber")
    public CustomerAccountState refreshAccountStateInCache(CustomerAccount account) {

        Date startToday = DateUtil.atStartOfDay(new Date());
        CustomerAccountState state = new CustomerAccountState(
                account.getAccountNumber(),
                account.getBalance(),
                Optional.ofNullable(transactionRepo.countAllByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccountAndStatus(startToday, TransactionTypes.DEPOSIT, account, SUCCESS)).orElse(0),
                Optional.ofNullable(transactionRepo.sumTransactionsByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccount(startToday, TransactionTypes.DEPOSIT, account, SUCCESS)).orElse(0.0),
                Optional.ofNullable(transactionRepo.countAllByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccountAndStatus(startToday, TransactionTypes.WITHDRAW, account, SUCCESS)).orElse(0),
                Optional.ofNullable(transactionRepo.sumTransactionsByDateTimeCreatedIsAfterAndTransactionTypeAndCustomerAccount(startToday, TransactionTypes.WITHDRAW, account, SUCCESS)).orElse(0.0));

        log.info("refreshed account state {}", state);
        return state;
    }

    public void clear() {
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
