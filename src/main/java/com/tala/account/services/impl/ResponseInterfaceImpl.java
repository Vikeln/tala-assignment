package com.tala.account.services.impl;

import com.tala.account.domain.models.Response;
import com.tala.account.domain.models.Status;
import com.tala.account.services.ResponseInterface;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseInterfaceImpl implements ResponseInterface {

    @Override
    public ResponseEntity accountBalanceResponse(Optional byId) {
        return null;
    }

    @Override
    public ResponseEntity accountResponse(Optional optional) {
        return null;
    }

    @Override
    public ResponseEntity<Status> statusResponse(Status status) {

        if (status.getCode() == Response.SUCCESS.status().getCode())
            return ResponseEntity.ok(status);
        else
            return ResponseEntity.badRequest().body(status);
    }
}
