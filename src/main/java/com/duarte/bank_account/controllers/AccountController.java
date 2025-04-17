package com.duarte.bank_account.controllers;

import com.duarte.bank_account.domain.model.Transfer;
import com.duarte.bank_account.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam(name = "accountNumber") Integer accountNumber,
                                     @RequestParam(name = "amount") BigDecimal amount,
                                     @RequestParam(name = "token") String token){

        try{
            accountService.deposit(accountNumber, amount, token);
            return ResponseEntity.status(HttpStatus.OK).body("Deposit successfully!");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam(name = "fromAccountNumber") Integer fromAccountNumber,
                                      @RequestParam(name = "toAccountNumber") Integer toAccountNumber,
                                      @RequestParam(name = "amount") BigDecimal amount,
                                      @RequestParam(name = "fromToken") String fromToken){

        try{
            accountService.transfer(fromAccountNumber, toAccountNumber, amount, fromToken);
            return ResponseEntity.status(HttpStatus.OK).body("Transfer successfully!");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts(){
        try{
            var response = accountService.getAllAccounts();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
