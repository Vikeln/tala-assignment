package com.tala.account.services.impl;

import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.mappers.DomainMapper;
import com.tala.account.domain.models.Response;
import com.tala.account.domain.models.Status;
import com.tala.account.persistence.entities.CustomerAccount;
import com.tala.account.repos.AccountRepo;
import com.tala.account.services.AccountService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class AccountServiceImpl extends ResponseInterfaceImpl implements AccountService {
    private DomainMapper mapper = Mappers.getMapper(DomainMapper.class);
    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Value("${default.maxDepositAmount}")
    private double maxDpositAmount;

    @Autowired
    private AccountRepo accountRepo;

    @Value("${default.account.transactions}")
    private Integer defaultAccountTransactions;

    @Value("${default.account.depositamount}")
    private double defaultAccountDepositAmount;

    @Value("${default.account.withdrawals.amount}")
    private double defaultAccountWithdrawalsMaxAmount;

    @Value("${default.account.withdrawals.today}")
    private double defaultAccountWithdrawalsToday;

    @Value("${default.account.withdrawals.frequency}")
    private double defaultAccountWithdrawalsFrequency;

    @Override
    public ResponseEntity transact(TransactionTypes transactionType, String accountNumber, double amount, HttpServletRequest request) {
        Optional<CustomerAccount> customerAccount = accountRepo.findById(accountNumber);
        Status status;
        if (customerAccount.isPresent()) {

            status = initialTransactionChecks(customerAccount.get(), transactionType, amount);
            if (status.equals(Response.SUCCESS)) {
                status = transact(customerAccount.get(), transactionType, amount);
                return ResponseEntity.ok(status);
            }
        } else
            status = Response.ACCOUNT_NOT_FOUND.status();

        return ResponseEntity.ok(status);
    }


    public Status transact(CustomerAccount account, TransactionTypes transactionType, double amount) {

        if (transactionType.equals(TransactionTypes.DEPOSIT))
            account.setBalance(account.getBalance() - amount);
        else
            account.setBalance(account.getBalance() + amount);

        account = accountRepo.save(account);
        logger.info("Account response {}",account);

        return Response.SUCCESS.status();
    }

    public Status initialTransactionChecks(CustomerAccount account, TransactionTypes transactionType, double amount) {

        if (transactionType.equals(TransactionTypes.DEPOSIT)) {
//            check max deposti amount
            if (amount > maxDpositAmount)
                return Response.EXCEED_MAX_DEP_AMOUNT.status();
//            check max deposit times
            else if (account.getTransactionsToday() + 1 > defaultAccountTransactions)
                return Response.EXCEED_MAX_DEPOSITS.status();
//            check max deposti amount today
            else if ((account.getTotalDepositToday() + amount) > defaultAccountDepositAmount)
                return Response.EXCEED_MAX_DEPOSITS.status();
        } else if (transactionType.equals(TransactionTypes.WITHDRAW)) {
//            max withdrawal for the day = $50K
            if (amount > defaultAccountWithdrawalsMaxAmount)
                return Response.EXCEED_MAX_WITHDRAWALS.status();
//              ■ Max withdrawal per transaction = $20K
            else if ((account.getWithdrawalsToday() + amount) > defaultAccountWithdrawalsToday)
                return Response.EXCEED_MAX_WITHDRAWALS_TOTAL_TODAY.status();
//              ■ Max withdrawal frequency = 3 transactions/day
            else if ((account.getTotalWithdrawalsToday() + amount) > defaultAccountWithdrawalsFrequency)
                return Response.EXCEED_MAX_WITHDRAWALS_TODAY.status();
//              ■ Cannot withdraw when balance is less than withdrawal amount
            else if ((account.getTotalWithdrawalsToday() + amount) > account.getBalance())
                return Response.EXCEED_MAX_WITHDRAWALS_TODAY.status();
        }
        return Response.UNKNOWN_TRANSACTION_TYPE.status();
    }

    @Override
    public ResponseEntity accountBalanceResponse(String byId) {
        Optional<CustomerAccount> account = accountRepo.findById(byId);
        if (account.isPresent())
            return ResponseEntity.ok(mapper.formatCustomerAccount(account.get()));
        return ResponseEntity.badRequest().body(Response.ACCOUNT_NOT_FOUND.status());
    }

}