package com.example.microchatnotificationservice.application.exceptions;

public class ForbiddenActionException extends BusinessException {
    public ForbiddenActionException(String message) {
        super(message);
    }
}