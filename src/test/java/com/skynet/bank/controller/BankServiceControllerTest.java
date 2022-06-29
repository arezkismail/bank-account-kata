package com.skynet.bank.controller;

import com.skynet.bank.domain.model.Account;
import com.skynet.bank.domain.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static java.time.LocalDateTime.now;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
public class BankServiceControllerTest {

    private static final String URL_BANK = "/api/bank/accounts/";
    private static final UUID CODE_ACCOUNT = randomUUID();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @BeforeEach
    public void initMock() {
        openMocks(this);
        when(bankService.deposite(CODE_ACCOUNT, 10)).thenReturn(createAccount());
        when(bankService.withdrawal(CODE_ACCOUNT, 10)).thenReturn(createAccount());
    }

    @Test
    public void depositeAmount_withAccountMrid_shouldReturn200Status() throws Exception {
        //Given
        UUID codeAccount = CODE_ACCOUNT;

        //When
        mockMvc.perform(post(URL_BANK + "{codeAccount}/deposite/", codeAccount)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content("10")).andExpect(status().isOk());

        //Then
        verify(bankService, times(1)).deposite(codeAccount, 10);
    }

    @Test
    public void withdrawalAmount_withAccountMrid_shouldReturn200Status() throws Exception {
        //Given
        UUID codeAccount = CODE_ACCOUNT;

        //When
        mockMvc.perform(post(URL_BANK + "{codeAccount}/withdrawal/", codeAccount)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content("10")).andExpect(status().isOk());

        //Then
        verify(bankService, times(1)).withdrawal(codeAccount, 10);
    }

    @Test
    public void depositeAmount_withoutAccountMrid_shouldReturn400Status() throws Exception {
        //Given
        UUID codeAccount = null;

        //When
        mockMvc.perform(post(URL_BANK + "{codeAccount}/deposite/", codeAccount)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                //Then
                .content("10")).andExpect(status().is4xxClientError());
    }

    @Test
    public void displayOperations_withAccountMrid_shouldReturn200Status() throws Exception {
        //Given
        UUID codeAccount = CODE_ACCOUNT;

        //When
        mockMvc.perform(get(URL_BANK + "{codeAccount}/operations/", codeAccount)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)).andExpect(status().isOk());

        //Then
        verify(bankService, times(1)).displayOperations(codeAccount);
    }
    private Account createAccount() {
        return new Account(CODE_ACCOUNT, now(), 10);
    }

}
