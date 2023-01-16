package com.ssg.bank.domain;

import com.ssg.bank.domain.enums.OperationType;

import java.time.LocalDateTime;

public record Operation(OperationType type, Amount amount, LocalDateTime date) {
}
