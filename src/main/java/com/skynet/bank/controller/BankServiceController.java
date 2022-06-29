package com.skynet.bank.controller;

import com.skynet.bank.controller.dto.AccountDto;
import com.skynet.bank.domain.model.Account;
import com.skynet.bank.domain.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bank")
public class BankServiceController {

    private BankService bankService;

    @Autowired
    public BankServiceController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping(value = "/accounts/{accountCode}/deposite")
    public ResponseEntity<AccountDto> depositeAmount(@PathVariable("accountCode") UUID accountCode, @RequestBody double amount) {
        Account account = this.bankService.deposite(accountCode, amount);
        return new ResponseEntity<>(toAccountDto(account), HttpStatus.OK);
    }

    @PostMapping(value = "/accounts/{accountCode}/withdrawal")
    public ResponseEntity<AccountDto> withdrawalAmount(@PathVariable("accountCode") UUID accountCode, @RequestBody double amount) {
        Account account = this.bankService.withdrawal(accountCode, amount);
        return new ResponseEntity<>(toAccountDto(account), HttpStatus.OK);
    }

    @GetMapping( value = "/accounts/{accountCode}/operations")
    public ResponseEntity<String> getOperations(@PathVariable("accountCode") UUID accountCode) {
        return new ResponseEntity<>(this.bankService.displayOperations(accountCode), HttpStatus.OK);
    }

    private AccountDto toAccountDto(Account account) {
        return AccountDto.builder()
                .code(account.getCode())
                .creationDate(account.getCreationDate())
                .balance(account.getBalance())
                .build();
    }

}
