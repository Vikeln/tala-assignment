package com.tala.account.controllers;


import com.tala.account.domain.models.ValidationMessage;
import com.tala.account.domain.models.ValidationMessages;
import com.tala.account.services.impl.ResponseInterfaceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerValidationHandler extends ResponseInterfaceImpl {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ValidationMessages> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return validationResponse(new ValidationMessages("Validation error.", fieldErrors.stream()
                .map(fieldError -> processFieldError(fieldError))
                .collect(Collectors.toList())));
    }

    private ValidationMessage processFieldError(FieldError error) {
        ValidationMessage message = null;
        if (error != null) {
            String msg;
            try {
                msg = error.getDefaultMessage();
            } catch (Exception e) {
                msg = error.getDefaultMessage();
            }

            message = new ValidationMessage(error.getField(), msg);
        }
        return message;
    }
}