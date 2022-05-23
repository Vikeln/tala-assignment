package com.tala.account.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data

public class ValidationMessages {

    private String message;
    private List<ValidationMessage> errors;
}
