package com.duarte.bank_account.services;

import com.duarte.bank_account.domain.dto.AccountDTO;
import com.duarte.bank_account.domain.model.Account;
import com.duarte.bank_account.domain.model.Transfer;
import com.duarte.bank_account.repositories.AccountRepository;
import com.duarte.bank_account.repositories.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TokenService tokenService;
    private final TransferRepository transferRepository;

    @Transactional
    public void deposit(Integer accountNumber, BigDecimal amount, String token){
        //Validate the token
        String accountEmail = tokenService.validateToken(token);
        if(accountEmail == null){
            throw new IllegalArgumentException("The token is invalid.");
        }

        //Find and validation account
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account number not found!"));

        if(!account.getEmail().matches(accountEmail)){
            throw new IllegalArgumentException("Token doesn't match account");
        }

        //Validate Amount
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("The deposit amount must be greater than 0.");
        }

        //Deposit
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(Integer fromAccountNumber, Integer toAccountNumber, BigDecimal amount, String fromToken){
        //Validate the token of fromAccount
        String fromAccountEmail = tokenService.validateToken(fromToken);
        if(fromAccountEmail == null){
            throw new IllegalArgumentException("Invalid token for source account!");
        }

        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        if(!fromAccount.getEmail().matches(fromAccountEmail)){
            throw new IllegalArgumentException("Token doesn't match source account!");
        }

        //Check if received account number exists
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found!"));

        //Check if there is sufficient balance
        if(fromAccount.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Insufficient balance");
        }

        //Update balance
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        Transfer transfer = Transfer.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .transferDateTime(generateCreationDate())
                .build();

        transferRepository.save(transfer);
        accountRepository.saveAll(Arrays.asList(fromAccount, toAccount));
    }

    @Transactional
    public void withdraw(Integer accountNumber, BigDecimal amount, String token){
        //Validate the token of account
        String accountEmail = tokenService.validateToken(token);
        if(accountEmail == null){
            throw new IllegalArgumentException("Invalid token for source account!");
        }

        //Verify if account number exists
        Account findAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found!"));

        if (!findAccount.getEmail().matches(accountEmail)) {
            throw new IllegalArgumentException("Token doesn't match source account!");
        }

        //Check if there is sufficient balance
        if(findAccount.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Insufficient balance");
        }

        //Withdraw
        findAccount.setBalance(findAccount.getBalance().subtract(amount));

        accountRepository.save(findAccount);
    }

    public List<AccountDTO> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();

        if(accounts.isEmpty()){
            throw new RuntimeException("No accounts registered in the system.");
        }

        return accounts.stream().map(account -> new AccountDTO(
                account.getId(),
                account.getFullName(),
                account.getBalance(),
                account.getAccountNumber(),
                account.getCreationDate()
        )).collect(Collectors.toList());
    }

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
