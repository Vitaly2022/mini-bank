package com.vit.minibank.domain.exceptions;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("На счете недостаточно средств");
    }
}
