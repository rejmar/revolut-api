package com.mr.revolut.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionDTO {
    private long senderId;
    private long recipientId;
    private String amount;
}
