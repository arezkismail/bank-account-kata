package com.skynet.bank.domain.service.impl;

import com.skynet.bank.domain.exception.AccountNotFoundException;
import com.skynet.bank.domain.model.Account;
import com.skynet.bank.domain.model.Operation;
import com.skynet.bank.domain.service.BankService;
import com.skynet.bank.domain.spi.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BankServiceImpl implements BankService {

    private static final String OPERATION_HEADER = "Operation | date | amount | balance";
    private static final String SEPARATOR = " | ";
    private static final String DATE_FORMAT = "dd/MM/YYYY";
    private DateTimeFormatter dTF = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private AccountRepository accountRepository;

    @Autowired
    public BankServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account deposite(UUID accountCode, double amount) {
        Optional<Account> optionalAccount = this.accountRepository.findById(accountCode);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(String.format("This account %s not found !", accountCode));
        }
        // save amount and operation
        Account account = optionalAccount.get();
        account.addAmount(amount);
        this.accountRepository.save(account);
        return account;
    }

    @Override
    public Account withdrawal(UUID accountCode, double amount) {
        Optional<Account> optionalAccount = this.accountRepository.findById(accountCode);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(String.format("This account %s not found !", accountCode));
        }

        Account account = optionalAccount.get();
        account.addWithdrawal(amount);
        this.accountRepository.save(account);
        return account;
    }

    public String displayOperations(UUID accountCode) {
        Optional<Account> optionalAccount = this.accountRepository.findById(accountCode);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(String.format("This account %s not found !", accountCode));
        }
        Account account = optionalAccount.get();
        List<Operation> operations = account.getOperations();
        String header = OPERATION_HEADER + "\n";
        String result = header;
        for (Operation operation : operations) {
            result = result.concat(operation.toString()).concat("\n");
        }
        return result;
    }
}
