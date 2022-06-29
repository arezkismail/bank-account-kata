package com.skynet.bank.domain.spi;

import com.skynet.bank.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Optional<Account> findById(UUID accountCode);

    Account save(Account account);
}
