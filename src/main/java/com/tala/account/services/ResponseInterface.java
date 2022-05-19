package com.tala.account.services;

import com.tala.account.domain.models.Status;
import com.tala.account.persistence.entities.CustomerAccount;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ResponseInterface {

    ResponseEntity accountBalanceResponse(Optional<CustomerAccount> byId);

    ResponseEntity accountResponse(Optional<CustomerAccount> account);

    ResponseEntity<Status> statusResponse(Status status);
}
