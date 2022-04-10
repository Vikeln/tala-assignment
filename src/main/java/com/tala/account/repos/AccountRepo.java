package com.tala.account.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tala.account.persistence.entities.*;

public interface AccountRepo extends JpaRepository<CustomerAccount, String> {

}
