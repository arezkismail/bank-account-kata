package com.skynet.bank.domain.model;

import com.skynet.bank.domain.enums.OperationTypeEnum;
import com.skynet.bank.domain.exception.InsufficientAmountException;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

@Getter
@Setter
public class Account implements Serializable {

    private UUID code;
    private LocalDateTime creationDate;
    private double balance;

    private Customer client;

    private List<Operation> operations = new ArrayList<>();


    public Account(UUID code, LocalDateTime creationDate, double balance) {
        super();
        this.code = code;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public void addAmount(double amount) {
        if (amount <= 0) {
            throw new InsufficientAmountException("This amount must be greater than 0 !");
        }
        this.balance = this.balance + amount;
        this.operations.add(new Operation(randomUUID(), now(), OperationTypeEnum.DEPOSIT, amount, this.balance));
    }

    public void addWithdrawal(double amount) {
        if (amount <= 0) {
            throw new InsufficientAmountException("This amount must be greater than 0 !");
        }
        if (this.balance >= amount) {
            this.balance = this.balance - amount;
            this.operations.add(new Operation(randomUUID(), now(), OperationTypeEnum.WITHDRAWAL, amount, this.balance));
        } else {
            throw new RuntimeException("You do not have enough money left to complete this operation !");
        }
    }
}
