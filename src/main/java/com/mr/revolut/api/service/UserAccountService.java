package com.mr.revolut.api.service;

import com.mr.revolut.api.database.DummyDB;
import com.mr.revolut.api.exception.RevolutApiException;
import com.mr.revolut.api.model.UserAccount;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.Set;

@Log4j2
public class UserAccountService {

    public Set<UserAccount> getAllUsers() {
        return DummyDB.getUserAccounts();
    }

    public UserAccount findUserAccount(long id) throws RevolutApiException {
        log.info("Looking for User Account with id = " + id);
        Optional<UserAccount> userAccount = getAllUsers().stream().filter(account -> account.getId() == id).findFirst();

        if (userAccount.isPresent()) {
            log.info("User Account with id " + id + " found");
            return userAccount.get();
        } else {
            throw new RevolutApiException("User Account not found! Id = " + id);
        }
    }
}
