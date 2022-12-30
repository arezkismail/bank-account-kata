package com.ssg.bank.exception;

public class NegativeInitialBalanceException extends RuntimeException{

    public NegativeInitialBalanceException(String message) {
        super(message);
    }
}
