package com.vit.minibank.domain.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("Проверьте данные: номер счета и PIN");
    }
}
