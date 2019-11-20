package com.mr.revolut.api.service;

import com.mr.revolut.api.dao.Transaction;
import com.mr.revolut.api.dao.UserAccount;
import com.mr.revolut.api.database.DummyDB;
import com.mr.revolut.api.exception.RevolutApiException;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

@Log4j2
public class UserAccountService {

    public Map<Long, UserAccount> getAllUsers() {
        return DummyDB.getUserAccounts();
    }

    public UserAccount findUserAccount(long id) throws RevolutApiException {
        log.info("Looking for User Account with id = " + id);
        Optional<UserAccount> userAccount = getAllUsers().values().stream().filter(account -> account.getId() == id).findFirst();

        if (userAccount.isPresent()) {
            log.info("User Account with id " + id + " found");
            return userAccount.get();
        } else {
            throw new RevolutApiException("User Account not found! Id = " + id);
        }
    }

    public synchronized UserAccount createUserAccount(String balance) throws RevolutApiException {
        long nextId = DummyDB.getNextId();
        UserAccount newAccount = new UserAccount(nextId, new BigDecimal(balance));
        log.info("New Account created: " + newAccount);
        DummyDB.getUserAccounts().put(nextId, newAccount);
        log.info("New Account persisted to database");
        return newAccount;
    }

    public void withdraw(UserAccount account, String amount) throws RevolutApiException {
        BigDecimal transactionAmount = new BigDecimal(amount);
        if (!verifyAmount(transactionAmount))
            throw new RevolutApiException("Amount to withdraw must be greater than 0!");
        BigDecimal result = account.getBalance().subtract(transactionAmount);

        if (verifyAmount(result)) {
            makeTransaction(account, transactionAmount.negate(), result);
        } else {
            throw new RevolutApiException("The balance for account with id = " + account.getId()
                    + " is too low! Current balance = " + account.getBalance()
                    + ", requested amount to withdraw = " + amount);
        }

    }

    private boolean verifyAmount(BigDecimal result) {
        return result.compareTo(BigDecimal.ZERO) >= 0;
    }

    public void deposit(UserAccount account, String amount) throws RevolutApiException {
        BigDecimal transactionAmount = new BigDecimal(amount);
        BigDecimal result = account.getBalance().add(transactionAmount);

        log.info("Verifying amount to deposit...");
        if (verifyAmount(transactionAmount)) {
            log.info("Amount verified");
            makeTransaction(account, transactionAmount, result);
        } else {
            throw new RevolutApiException("Cannot deposit less than 0");
        }

    }

    private synchronized void makeTransaction(UserAccount account, BigDecimal transactionAmount, BigDecimal result) {
        account.setBalance(result);

        log.info("Creating new transaction...");
        OptionalLong optMaxTransactionId = DummyDB.getMaxTransactionId();
        long maxTransactionId = 0;
        if (optMaxTransactionId.isPresent()) maxTransactionId = optMaxTransactionId.getAsLong();
        Transaction newTransaction = new Transaction(maxTransactionId, account.getId(), account.getId(), transactionAmount, OffsetDateTime.now().toString());
        account.getTransactions().add(newTransaction);
        log.info("Transaction created and assigned to account with id = " + account.getId() + ". Transaction: " + newTransaction);
    }

    public UserAccount deleteUser(long userId) throws RevolutApiException {
        log.info("Looking for user with id = " + userId);
        UserAccount userToDelete = Optional.ofNullable(DummyDB.getUserAccounts().get(userId)).orElseThrow(() -> new RevolutApiException("User with id = " + userId + " not found!"));
        log.info("User found!");
        log.info("Removing user");
        DummyDB.getUserAccounts().remove(userId);
        log.info("User removed. " + userToDelete);
        return userToDelete;
    }
}
