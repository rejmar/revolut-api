package com.mr.revolut.api.service;

import com.mr.revolut.api.database.DummyDB;
import com.mr.revolut.api.exception.RevolutApiException;
import com.mr.revolut.api.model.UserAccount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountServiceTest {
    private static UserAccountService userAccountService;
    private UserAccount testUser;

    @BeforeAll
    static void prepareDatabase() {
        userAccountService = new UserAccountService();
    }

    @BeforeEach
    void beforeEach() {
        testUser = new UserAccount(2L, new BigDecimal("1000.00"));
    }

    @Test
    @DisplayName("test getting all users from the database")
    public void getAllUserAccountsTest() {
        assertNotNull(userAccountService.getAllUsers());
    }

    @Test
    @DisplayName("test finding existing user from the database")
    public void validfindUserAccountTest() {
        assertDoesNotThrow(() -> userAccountService.findUserAccount(1L));
    }

    @Test
    @DisplayName("test finding non-existing user from the database")
    public void invalidfindUserAccountTest() {
        assertThrows(RevolutApiException.class, () -> userAccountService.findUserAccount(99L));
    }

    @Test
    @DisplayName("test creating new user account")
    public void creatingNewUserAccountTest() {
        assertDoesNotThrow(() -> userAccountService.createUserAccount("2000.00"));
    }

    @Test
    @DisplayName("test valid deposit on user account")
    public void validDepositTest() {
        assertDoesNotThrow(() -> userAccountService.deposit(testUser, "200.00"));
        assertEquals(new BigDecimal("1200.00"), testUser.getBalance());
    }

    @Test
    @DisplayName("test invalid deposit on user account")
    public void invalidDepositTest() {
        assertThrows(RevolutApiException.class, () -> userAccountService.deposit(testUser, "-200.00"));
    }

    @Test
    @DisplayName("test valid withdraw from user account")
    public void validWithdrawTest() {
        assertDoesNotThrow(() -> userAccountService.withdraw(testUser, "200.00"));
        assertEquals(new BigDecimal("800.00"), testUser.getBalance());
    }

    @Test
    @DisplayName("test invalid (negative value) withdraw from user account")
    public void invalidValueWithdrawTest() {
        assertThrows(RevolutApiException.class, () -> userAccountService.withdraw(testUser, "-200.00"));
    }

    @Test
    @DisplayName("test invalid (insufficient funds) withdraw from user account")
    public void insufficientFundsWithdrawTest() {
        assertThrows(RevolutApiException.class, () -> userAccountService.withdraw(testUser, "1200.00"));
    }

    @Test
    @DisplayName("test valid deletion of user account")
    public void validDeleteTest() {
        DummyDB.getUserAccounts().put(90L, new UserAccount(90L, new BigDecimal("1000.00")));
        assertDoesNotThrow(() -> userAccountService.deleteUser(90L));
        assertFalse(DummyDB.getUserAccounts().containsKey(90L));
    }

    @Test
    @DisplayName("test invalid (no user account) deletion of user account")
    public void invalidDeleteTest() {
        assertThrows(RevolutApiException.class, () -> userAccountService.deleteUser(99L));
    }

}