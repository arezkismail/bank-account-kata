package com.ssg.bank.domain;

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
}
