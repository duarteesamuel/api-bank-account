package com.duarte.bank_account.domain.dto;

import com.duarte.bank_account.domain.model.Transfer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record AccountDTO(
        String accountId,
        String fullName,
        BigDecimal balance,
        Integer accountNumber,
        Instant creationDate,
        List<Transfer> sentTransfers,
        List<Transfer> receivedTransfer
) {
}
