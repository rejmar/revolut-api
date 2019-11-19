package com.mr.revolut.api.service;

import com.mr.revolut.api.database.DummyDB;
import com.mr.revolut.api.dto.UserTransactionDTO;
import com.mr.revolut.api.exception.RevolutApiException;
import com.mr.revolut.api.model.Transaction;
import com.mr.revolut.api.model.UserAccount;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.OptionalLong;

@Log4j2
public class UserTransactionService {

    public void transfer(UserTransactionDTO userTransactionDTO) throws RevolutApiException {
        log.info("Looking for sender with id = " + userTransactionDTO.getSenderId());
        Optional<UserAccount> optSender = Optional.ofNullable(DummyDB.findUser(userTransactionDTO.getSenderId()));
        if (!optSender.isPresent())
            throw new RevolutApiException("Sender with id = " + userTransactionDTO.getSenderId() + " not found!");
        log.info("Founded!");
        UserAccount sender = optSender.get();

        log.info("Looking for recipient with id = " + userTransactionDTO.getRecipientId());
        Optional<UserAccount> optRecipient = Optional.ofNullable(DummyDB.findUser(userTransactionDTO.getRecipientId()));
        if (!optRecipient.isPresent())
            throw new RevolutApiException("Recipient with id = " + userTransactionDTO.getRecipientId() + " not found!");
        log.info("Founded!");
        UserAccount recipient = optRecipient.get();

        BigDecimal amountToTransfer = new BigDecimal(userTransactionDTO.getAmount());

        if (amountToTransfer.compareTo(BigDecimal.ZERO) <= 0)
            throw new RevolutApiException("Cannot process the transfer! Amount MUST be positive value! Amount = " + amountToTransfer.toString());

        synchronized (this) {
            if (sender.getBalance().subtract(amountToTransfer).compareTo(BigDecimal.ZERO) < 0)
                throw new RevolutApiException("Not sufficient funds on sender's account! Sender's balance = " + sender.getBalance() + ", money to transfer = " + amountToTransfer.toString());

            BigDecimal newSenderBalance = sender.getBalance().subtract(amountToTransfer);
            BigDecimal newRecipientBalance = recipient.getBalance().add(amountToTransfer);

            OptionalLong optMaxTransactionId = DummyDB.getMaxTransactionId();
            long maxTransactionId = 0;
            if (optMaxTransactionId.isPresent()) maxTransactionId = optMaxTransactionId.getAsLong();

            Transaction newTransaction = new Transaction(maxTransactionId, sender.getId(), recipient.getId(), new BigDecimal(userTransactionDTO.getAmount()), OffsetDateTime.now().toString());

            sender.setBalance(newSenderBalance);
            sender.getTransactions().add(newTransaction);
            recipient.setBalance(newRecipientBalance);
            recipient.getTransactions().add(newTransaction);
        }
    }
}
