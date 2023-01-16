package com.ssg.bank.domain;

import java.math.BigDecimal;

import com.ssg.bank.exception.BalanceNotSufficientException;
import com.ssg.bank.exception.NegativeInitialBalanceException;


public record Balance(BigDecimal value) {
    public Balance {
        if (value.signum() == -1) throw new NegativeInitialBalanceException("the balance must not be negative");
    }

    public Balance add(Amount amount) {
        return new Balance(value.add(amount.value()));
    }

    public Balance substact(Amount amount) {
        if(value.compareTo(amount.value()) == -1) {
            throw new BalanceNotSufficientException("balance not sufficient");
        }
        return new Balance(value.subtract(amount.value()));
    }
}
