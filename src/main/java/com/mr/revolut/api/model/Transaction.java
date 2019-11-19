package com.mr.revolut.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Transaction {
    @JsonIgnore
    private long id;
    @JsonProperty(required = true)
    private long senderId;
    @JsonProperty(required = true)
    private long recipientId;
    @JsonProperty(required = true)
    private BigDecimal amount;
    @JsonProperty(required = true)
    private String transactionDate;
}
