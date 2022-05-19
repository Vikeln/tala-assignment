package com.tala.account.persistence.repos;


import com.tala.account.persistence.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<CustomerAccount, String> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<CustomerAccount> findById(String s);
}
