package com.ssg.bank.domain;

import com.ssg.bank.exception.BalanceNotSufficientException;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static com.ssg.bank.domain.enums.OperationType.DEPOSIT;
import static com.ssg.bank.domain.enums.OperationType.WITHDRAWAL;
import static java.time.LocalDateTime.now;

public class Account {

    private Balance balance;
    private LocalDateTime creationDate;
    private List<OperationLine> operationLines;

    public Account(Balance balance, LocalDateTime creationDate) {
        this.balance = balance;
        this.creationDate = creationDate;
        this.operationLines = new LinkedList<>();
    }

    public Balance getBalance() {
        return balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void deposit(Amount amount) {
        balance = balance.add(amount.value());
        operationLines.add(new OperationLine(new Operation(DEPOSIT, amount, now()), balance));
    }

    public void withdrawal(Amount amount) {
        if(balance.value().compareTo(amount.value()) == -1) {
            throw new BalanceNotSufficientException("balance not sufficient");
        }
        balance = balance.substact(amount);
        operationLines.add(new OperationLine(new Operation(WITHDRAWAL, amount, now()), balance));
    }

    public List<OperationLine> getOperationLines() {
        return operationLines;
    }

    public void print(IOperationPrinter operationPrinter) {
        operationPrinter.print(operationLines);
    }
}
