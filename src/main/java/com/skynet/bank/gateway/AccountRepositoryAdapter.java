package com.skynet.bank.gateway;

import com.skynet.bank.domain.model.Account;
import com.skynet.bank.domain.spi.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    Map<UUID, Account> mapAccount = new HashMap();

    @Override
    public Optional<Account> findById(UUID accountCode) {
        Account account = mapAccount.get(accountCode);
        if(account == null) return Optional.empty();
        return Optional.of(account);
    }

    @Override
    public Account save(Account account) {
        this.mapAccount.put(account.getCode(), account);
        return this.mapAccount.get(account.getCode());
    }
}
