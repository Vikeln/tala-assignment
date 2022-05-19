package com.tala.account.services;

import com.tala.account.domain.models.Status;
import com.tala.account.domain.models.ValidationMessages;
import org.springframework.http.ResponseEntity;

public interface ResponseInterface {

    ResponseEntity<Status> statusResponse(Status status);

    ResponseEntity validationResponse(ValidationMessages entity);
}
