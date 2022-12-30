package com.ssg.bank.exception;

public class BalanceNotSufficientException  extends RuntimeException{

    public BalanceNotSufficientException(String message) {
        super(message);
    }
}