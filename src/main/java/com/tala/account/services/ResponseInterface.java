package com.tala.account.services;

import com.tala.account.persistence.entities.CustomerAccount;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public interface ResponseInterface<X,T extends  Object> {

    ResponseEntity accountBalanceResponse(Optional<CustomerAccount> byId);

    ResponseEntity accountResponse(Optional<CustomerAccount> account);
}
