package com.duarte.bank_account.domain.dto;


import java.math.BigDecimal;
import java.time.Instant;

public record AccountDTO(
        String accountId,
        String fullName,
        BigDecimal balance,
        Integer accountNumber,
        Instant creationDate
) {
}
