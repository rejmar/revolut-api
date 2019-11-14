package com.mr.revolut.api.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAccount {
    private long id;
    private BigDecimal balance;
}
