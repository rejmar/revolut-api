package com.mr.revolut.api.service;

import com.mr.revolut.api.database.DummyDB;
import com.mr.revolut.api.dto.UserTransactionDTO;
import com.mr.revolut.api.exception.RevolutApiException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserTransactionServiceTest {
    private static UserTransactionService userTransactionService;
    private UserTransactionDTO userTransactionDTO;

    @BeforeAll
    static void beforeAll() {
        userTransactionService = new UserTransactionService();
    }


    @Test
    @DisplayName("test valid transaction between two user accounts")
    void validTransactionTest() {
        DummyDB.getUserAccounts().get(1L).setBalance(new BigDecimal("100.00"));
        DummyDB.getUserAccounts().get(2L).setBalance(new BigDecimal("100.00"));
        userTransactionDTO = new UserTransactionDTO(2L, 1L, "100.00");

        assertDoesNotThrow(() -> userTransactionService.transfer(userTransactionDTO));
        assertEquals(new BigDecimal("200.00"), DummyDB.getUserAccounts().get(1L).getBalance());
        assertEquals(new BigDecimal("0.00"), DummyDB.getUserAccounts().get(2L).getBalance());
    }

    @Test
    @DisplayName("test invalid (not found sender) transaction between two user accounts")
    void notFoundSenderInvalidTest() {
        userTransactionDTO = new UserTransactionDTO(99L, 2L, "100");
        assertThrows(RevolutApiException.class, () -> userTransactionService.transfer(userTransactionDTO));
    }

    @Test
    @DisplayName("test invalid (inot found recipient) transaction between two user accounts")
    void notFoundRecipientInvalidTest() {
        userTransactionDTO = new UserTransactionDTO(1L, 99L, "100");
        assertThrows(RevolutApiException.class, () -> userTransactionService.transfer(userTransactionDTO));
    }

    @Test
    @DisplayName("test invalid (amount = 0) transaction between two user accounts")
    void zeroTransferAmountInvalidTest() {
        userTransactionDTO = new UserTransactionDTO(1L, 2L, "0");
        assertThrows(RevolutApiException.class, () -> userTransactionService.transfer(userTransactionDTO));
    }

    @Test
    @DisplayName("test invalid (amount < 0) transaction between two user accounts")
    void negativeTransferAmountInvalidTest() {
        userTransactionDTO = new UserTransactionDTO(1L, 2L, "-100");
        assertThrows(RevolutApiException.class, () -> userTransactionService.transfer(userTransactionDTO));
    }

    @Test
    @DisplayName("test invalid (insufficient funds) transaction between two user accounts")
    void insufficientFundsTransferInvalidTest() {
        userTransactionDTO = new UserTransactionDTO(1L, 2L, "1000");
        assertThrows(RevolutApiException.class, () -> userTransactionService.transfer(userTransactionDTO));
    }
}