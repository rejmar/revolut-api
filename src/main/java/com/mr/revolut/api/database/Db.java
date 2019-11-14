package com.mr.revolut.api.database;

import com.mr.revolut.api.entity.UserAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Db {
    private static Map<Long, UserAccount> userAccounts = new ConcurrentHashMap<>();

    static {
        userAccounts.put(1L, new UserAccount());
    }
}
