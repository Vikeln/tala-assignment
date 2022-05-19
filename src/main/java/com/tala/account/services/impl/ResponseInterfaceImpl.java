package com.tala.account.services.impl;

import com.tala.account.domain.models.Response;
import com.tala.account.domain.models.Status;
import com.tala.account.domain.models.ValidationMessages;
import com.tala.account.services.ResponseInterface;
import org.springframework.http.ResponseEntity;

public class ResponseInterfaceImpl implements ResponseInterface {

    @Override
    public ResponseEntity<Status> statusResponse(Status status) {

        if (status.getCode() == Response.SUCCESS.status().getCode())
            return ResponseEntity.ok(status);
        else
            return ResponseEntity.badRequest().body(status);
    }

    @Override
    public ResponseEntity validationResponse(ValidationMessages entity) {
        return ResponseEntity.badRequest().body(entity);
    }
}
