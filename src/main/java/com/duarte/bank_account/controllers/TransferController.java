package com.duarte.bank_account.controllers;

import com.duarte.bank_account.domain.model.Transfer;
import com.duarte.bank_account.services.AccountService;
import com.duarte.bank_account.services.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping
    public ResponseEntity<?> getAllTransfers(){
        try{
            var response = transferService.getAllTransfers();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
