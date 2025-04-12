package com.duarte.bank_account.domain.dto;

public record AccountRequestDTO(
        String fullName,
        String email,
        String password) {
}
