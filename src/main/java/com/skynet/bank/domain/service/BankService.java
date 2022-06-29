package com.skynet.bank.domain.service;

import com.skynet.bank.domain.model.Account;

import java.util.UUID;

public interface BankService {

    Account deposite(UUID accountCode, double amount);

    Account withdrawal(UUID accountCode, double amount);

    String displayOperations(UUID accountCode);

}
