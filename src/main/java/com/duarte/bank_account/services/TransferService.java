package com.duarte.bank_account.services;

import com.duarte.bank_account.domain.model.Transfer;
import com.duarte.bank_account.repositories.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    public List<Transfer> getAllTransfers(){
        return Optional.of(transferRepository.findAll())
                .filter(transfers -> !transfers.isEmpty())
                .orElseThrow(() -> new RuntimeException("No transfers made!"));
    }
}
