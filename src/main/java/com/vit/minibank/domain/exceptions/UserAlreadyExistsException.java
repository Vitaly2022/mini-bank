package com.vit.minibank.domain.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String login) {
        super("Пользователь с таким логином: " + login + " уже существует");
    }
}
