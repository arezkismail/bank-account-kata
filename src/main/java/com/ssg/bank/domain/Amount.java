package com.ssg.bank.domain;

import com.ssg.bank.exception.InsufficientAmountException;

import java.math.BigDecimal;

public record Amount(BigDecimal value) {

    public Amount {
        if (value.signum() != 1) throw new InsufficientAmountException("Amount should not be null or negative");
    }
}
