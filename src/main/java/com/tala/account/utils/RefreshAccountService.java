package com.tala.account.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class RefreshAccountService {

    @Autowired
    CacheConfig cacheConfig;

    @Scheduled(cron = "0 0 0 * * *") // scheduled to run at midnight everyday
    public void refreshAccount() {
        cacheConfig.clear();
    }

}
