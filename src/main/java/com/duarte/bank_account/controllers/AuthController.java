package com.duarte.bank_account.controllers;

import com.duarte.bank_account.domain.dto.AccountRequestDTO;
import com.duarte.bank_account.domain.dto.AccountResponseDTO;
import com.duarte.bank_account.domain.model.Account;
import com.duarte.bank_account.repositories.AccountRepository;
import com.duarte.bank_account.services.AccountService;
import com.duarte.bank_account.services.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AccountRequestDTO body){
        Optional<Account> account = this.accountRepository.findByEmail(body.email());

        if(account.isEmpty()){
            Account newAccount = Account.builder()
                    .fullName(body.fullName())
                    .creationDate(accountService.generateCreationDate())
                    .accountNumber(accountService.generateAccountNumber())
                    .email(body.email())
                    .password(body.password())
                    .balance(BigDecimal.ZERO)
                    .build();

            String token = this.tokenService.generateToken(newAccount);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AccountResponseDTO(newAccount.getFullName(),
                            newAccount.getEmail(),
                            newAccount.getCreationDate(),
                            newAccount.getAccountNumber(),
                            newAccount.getBalance(),
                            token));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
