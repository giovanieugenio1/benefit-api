package com.corporate.benefits.benefit_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfValidationException extends RuntimeException {

    public CpfValidationException(String message) {
        super(message);
    }
}
