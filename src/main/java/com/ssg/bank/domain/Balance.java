package com.ssg.bank.domain;

import java.math.BigDecimal;

import com.ssg.bank.exception.NegativeInitialBalanceException;


public record Balance(BigDecimal balance) {
    public Balance {
        if (balance.signum() == -1) throw new NegativeInitialBalanceException("the balance must not be negative");
    }

    public Balance add(BigDecimal amount) {
        return new Balance(balance.add(amount));
    }

}
