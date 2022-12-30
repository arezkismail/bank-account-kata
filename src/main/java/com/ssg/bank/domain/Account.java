package com.ssg.bank.domain;

import com.ssg.bank.exception.BalanceNotSufficientException;

import java.time.LocalDateTime;

public class Account {

    private Balance balance;
    private LocalDateTime creationDate;

    public Account(Balance balance, LocalDateTime creationDate) {
        this.balance = balance;
        this.creationDate = creationDate;
    }

    public Balance getBalance() {
        return balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void deposit(Amount amount) {
        balance = balance.add(amount.amount());
    }

    public void withdrawal(Amount amount) {
        if(balance.balance().compareTo(amount.amount()) == -1) {
            throw new BalanceNotSufficientException("balance not sufficient");
        }
        balance = balance.substact(amount);
    }
}
