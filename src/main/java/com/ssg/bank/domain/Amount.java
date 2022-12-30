package com.ssg.bank.domain;

import com.ssg.bank.exception.InsufficientAmountException;

import java.math.BigDecimal;

public record Amount(BigDecimal amount) {

    public Amount {
        if (amount.signum() != 1) throw new InsufficientAmountException("Amount should not be null or negative");
    }
}
