package com.tala.account.utils;

import com.tala.account.persistence.entities.CustomerAccount;
import com.tala.account.repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class RefreshAccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Scheduled(cron = "0 0 0 * * *")
    public void refreshAccount(){
        accountRepo.findAll().stream().forEachOrdered(account->refreshAccountData(account));
    }

    private void refreshAccountData(CustomerAccount customerAccount) {
        customerAccount.setDepositToday(0);
        customerAccount.setTotalDepositToday(0);
        customerAccount.setTransactionsToday(0);
        customerAccount.setTotalWithdrawalsToday(0);
        customerAccount.setWithdrawalsToday(0);
        accountRepo.save(customerAccount);
    }
}
