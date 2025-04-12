package com.duarte.bank_account.controllers;

import com.duarte.bank_account.domain.dto.AccountRequestDTO;
import com.duarte.bank_account.domain.dto.AccountResponseDTO;
import com.duarte.bank_account.domain.model.Account;
import com.duarte.bank_account.repositories.AccountRepository;
import com.duarte.bank_account.services.AccountService;
import com.duarte.bank_account.services.AuthService;
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

    private final TokenService tokenService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AccountRequestDTO body){
        try{
            AccountResponseDTO response = authService.register(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
