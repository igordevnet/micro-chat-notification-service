package com.example.microchatnotificationservice.application.exceptions;

public class NotificationNotFoundException extends BusinessException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
