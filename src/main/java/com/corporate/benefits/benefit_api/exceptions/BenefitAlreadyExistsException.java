package com.corporate.benefits.benefit_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BenefitAlreadyExistsException extends RuntimeException {

    public BenefitAlreadyExistsException(String message) {
        super(message);
    }
}
