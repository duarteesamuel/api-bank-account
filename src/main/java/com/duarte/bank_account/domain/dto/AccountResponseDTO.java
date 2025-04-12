package com.duarte.bank_account.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponseDTO(
        Integer accountNumber,
        BigDecimal balance,
        String token
) {
}
