package com.skynet.bank;

import com.skynet.bank.domain.model.Account;
import com.skynet.bank.domain.spi.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Bean
    CommandLineRunner initAccount(AccountRepository accountRepository) {
        return arg -> {
            accountRepository.save(new Account(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"), now(), 10));
        };
    }

}
