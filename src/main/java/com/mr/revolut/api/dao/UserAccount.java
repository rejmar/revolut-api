package com.mr.revolut.api.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserAccount {
    @JsonProperty(required = true)
    private long id;
    @JsonProperty(required = true)
    private BigDecimal balance;
    @JsonProperty(required = true)
    private List<Transaction> transactions;

    public UserAccount(long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }
}
