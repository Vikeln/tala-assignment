package com.tala.account.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Status {
    private int code;
    private String message;

    public Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
