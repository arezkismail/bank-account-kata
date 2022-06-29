package com.skynet.bank.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AccountDto implements Serializable {

    private UUID code;
    private LocalDateTime creationDate;
    private double balance;
}
