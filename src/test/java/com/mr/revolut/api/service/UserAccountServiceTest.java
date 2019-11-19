package com.mr.revolut.api.service;

import com.mr.revolut.api.model.UserAccount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class UserAccountServiceTest {
    private static Map<Long, UserAccount> testDatabase;

    @BeforeAll
    static void prepareDatabase() {
        testDatabase = new ConcurrentHashMap<>();
        testDatabase.put(1L, new UserAccount(1L, new BigDecimal("1000.00")));
        testDatabase.put(2L, new UserAccount(2L, new BigDecimal("2000.00")));
    }

    @Test
    public void withdrawalTest() {
//        assertEquals
//        UserAccount user1 = testDatabase.get(1L);
//        user1.withdraw

    }
}