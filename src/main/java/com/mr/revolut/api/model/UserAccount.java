package com.mr.revolut.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public UserAccount(long id, double balance) {
        this.id = id;
        this.balance = new BigDecimal(balance);
        this.transactions = new ArrayList<>();
    }
}
