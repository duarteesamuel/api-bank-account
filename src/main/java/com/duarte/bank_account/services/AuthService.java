package com.duarte.bank_account.services;

import com.duarte.bank_account.domain.dto.AccountRequestDTO;
import com.duarte.bank_account.domain.dto.AccountResponseDTO;
import com.duarte.bank_account.domain.model.Account;
import com.duarte.bank_account.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AccountResponseDTO register(AccountRequestDTO dto){
        Optional<Account> account = this.accountRepository.findByEmail(dto.email());

        if(account.isPresent()){
            throw new IllegalArgumentException("Email already exists.");
        }

        String encodedPassword = passwordEncoder.encode(dto.password());

        Account newAccount = Account.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .creationDate(accountService.generateCreationDate())
                .accountNumber(accountService.generateAccountNumber())
                .password(encodedPassword)
                .balance(BigDecimal.ZERO)
                .build();

        this.accountRepository.save(newAccount);

        String token = this.tokenService.generateToken(newAccount);

        return new AccountResponseDTO(
                newAccount.getAccountNumber(),
                newAccount.getBalance(),
                token
        );
    }
}
