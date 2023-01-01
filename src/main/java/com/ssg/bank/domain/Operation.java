package com.ssg.bank.domain;

import com.ssg.bank.domain.enums.OperationType;

import java.time.LocalDateTime;

public class Operation {
    private OperationType type;
    private Amount amount;
    private LocalDateTime date;

    public Operation(OperationType type, Amount amount, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public OperationType getType() {
        return type;
    }

    public Amount getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
