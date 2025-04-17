package com.duarte.bank_account.services;

import com.duarte.bank_account.domain.dto.TransferResponseDTO;
import com.duarte.bank_account.domain.model.Transfer;
import com.duarte.bank_account.repositories.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    public List<TransferResponseDTO> getAllTransfers(){
        List<Transfer> transfers = transferRepository.findAll();

        if(transfers.isEmpty()){
            throw new RuntimeException("No transfers found in the system.");
        }

        return transfers.stream().map(transfer -> new TransferResponseDTO(
                transfer.getId(),
                transfer.getFromAccount().getId(),
                transfer.getToAccount().getId(),
                transfer.getFromAccount().getFullName(),
                transfer.getToAccount().getFullName(),
                transfer.getAmount(),
                transfer.getTransferDateTime()
        )).collect(Collectors.toList());
    }
}
