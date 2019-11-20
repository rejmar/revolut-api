package com.mr.revolut.api.database;

import com.mr.revolut.api.dao.Transaction;
import com.mr.revolut.api.dao.UserAccount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DummyDBTest {
    private static Map<Long, Transaction> testTransactions;
    private static UserAccount testUser;

    @BeforeAll
    static void setUp() {
        testUser = new UserAccount(1L, new BigDecimal("100.00"));
        testTransactions = DummyDB.getTransactions();
        testTransactions.put(1L, new Transaction(1L, 1, 2, new BigDecimal("100"), "2019-11-19T23:17:07.965146500+01:00"));
        testTransactions.put(2L, new Transaction(2L, 1, 1, new BigDecimal("100").negate(), "2019-11-19T23:27:07.965146500+01:00"));
        testTransactions.put(3L, new Transaction(3L, 2, 2, new BigDecimal("500"), "2019-11-19T23:37:07.965146500+01:00"));

    }

    @Test
    @DisplayName("test finding existing user account in user accounts Map")
    void validCheckFindingUser() {
        assertNotNull(DummyDB.findUser(1L));
        assertEquals(testUser, DummyDB.findUser(1L));
    }

    @Test
    @DisplayName("test finding non-existing user account in user accounts Map")
    void invalidCheckFindingUser() {
        assertNull(DummyDB.findUser(9L));
    }

    @Test
    @DisplayName("test getting max id number for transactions Map")
    void checkGettingMaxTransactionId() {
        assertEquals(3L, DummyDB.getMaxTransactionId().getAsLong());
    }

}