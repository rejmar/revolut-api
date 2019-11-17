package com.mr.revolut.api.database;

import com.mr.revolut.api.model.UserAccount;
import lombok.Getter;

import java.util.*;

public class DummyDB {
    @Getter
    private static Set<UserAccount> userAccounts = Collections.synchronizedSet(new HashSet<>());
    static {
        userAccounts.add(new UserAccount(1L, 100.00));
        userAccounts.add(new UserAccount(2L, 500.00));
    }

//    public long getNextId() {
//        userAccounts.stream().max()
//    }
}
