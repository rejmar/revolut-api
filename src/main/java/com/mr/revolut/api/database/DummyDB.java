package com.mr.revolut.api.database;

import com.mr.revolut.api.model.Transaction;
import com.mr.revolut.api.model.UserAccount;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.OptionalLong;
import java.util.concurrent.ConcurrentHashMap;

public class DummyDB {
    @Getter
    private static Map<Long, UserAccount> userAccounts = new ConcurrentHashMap();

    @Getter
    private static Map<Long, Transaction> transactions = new ConcurrentHashMap();

    private static volatile long id;

    static {
        id = 2L;
        userAccounts.put(1L, new UserAccount(1L, new BigDecimal("100.00")));
        userAccounts.put(2L, new UserAccount(2L, new BigDecimal("500.00")));
    }

    public static UserAccount findUser(long id) {
        return userAccounts.get(id);
    }

    public static OptionalLong getMaxTransactionId() {
        return transactions.values().stream().mapToLong(Transaction::getId).max();
    }

    public static synchronized long getNextId() {
        return ++id;
    }
}
