package com.tala.account.utils;

import com.tala.account.persistence.entities.CustomerAccount;
import com.tala.account.persistence.repos.AccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class Initializer {
    Logger logger = LoggerFactory.getLogger(Initializer.class);

    @Autowired
    private AccountRepo accountRepo;

    @Value("${default.account.number}")
    private String defaultAccountNumber;

    @PostConstruct
    private void createDefaultAccount() {
        Optional<CustomerAccount> account = accountRepo.findById(defaultAccountNumber);
        if (!account.isPresent()) {
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setAccountNumber(defaultAccountNumber);
            customerAccount.setBalance(0);
            customerAccount = accountRepo.save(customerAccount);
            logger.info("Account created {}", customerAccount);
        }

    }
}
