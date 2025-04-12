package com.duarte.bank_account.services;

import com.duarte.bank_account.domain.dto.AccountRequestDTO;
import com.duarte.bank_account.domain.model.Account;
import com.duarte.bank_account.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    public Integer generateAccountNumber(){
        Random random = new Random();
        Set<Integer> generatedNumbers = new HashSet<>();

        int accountNumber;

        do{
            accountNumber = random.nextInt(9_999_999) + 1_000_000;
        } while(!generatedNumbers.add(accountNumber));

        return accountNumber;
    }

    public Instant generateCreationDate(){
        return LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"));
    }
}
