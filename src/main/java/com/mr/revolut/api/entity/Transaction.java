package com.mr.revolut.api.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {
    private long id;
    private long senderId;
    private long recipientId;
    private BigDecimal amount;
}
