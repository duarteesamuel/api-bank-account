package com.duarte.bank_account.domain.dto;


import java.math.BigDecimal;
import java.time.Instant;

public record TransferResponseDTO(Long id,
                                  String sentAccountId,
                                  String receivedAccountId,
                                  String sentFullName,
                                  String receivedFullName,
                                  BigDecimal amount,
                                  Instant transferDateTime
                                  ) {
}
