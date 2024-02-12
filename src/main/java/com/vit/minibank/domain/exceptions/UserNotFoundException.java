package com.vit.minibank.domain.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String login) {
        super(login + " не найден");
    }
}
