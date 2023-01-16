package com.ssg.bank.domain;

import java.time.Clock;

import static com.ssg.bank.domain.enums.OperationType.DEPOSIT;
import static com.ssg.bank.domain.enums.OperationType.WITHDRAWAL;
import static java.time.LocalDateTime.now;

public class Account {

    private Balance balance;
    private Clock clock;

    private Statement statement;

    public Account(Balance balance) {
        this.balance = balance;
        this.clock = Clock.systemDefaultZone();
        this.statement = new Statement();
    }

    public Account(Balance balance, Clock clock) {
        this.balance = balance;
        this.clock = clock;
        this.statement = new Statement();
    }

    public Balance getBalance() {
        return balance;
    }

    public void deposit(Amount amount) {
        balance = balance.add(amount);
        statement.addOperation(new Operation(DEPOSIT, amount, now(clock)), balance);
    }

    public void withdrawal(Amount amount) {
        balance = balance.substact(amount);
        statement.addOperation(new Operation(WITHDRAWAL, amount, now(clock)), balance);
    }

    public Statement getStatement() {
        return statement;
    }

    public void print(IOperationPrinter operationPrinter) {
        operationPrinter.print(statement);
    }
}
