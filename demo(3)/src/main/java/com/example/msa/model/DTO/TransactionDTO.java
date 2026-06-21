package com.example.msa.model.DTO;

import com.example.msa.etc.Status;
import com.example.msa.etc.TransactionType;

public record TransactionDTO(
        String txHash,
        Double amount,
        Double feeUsd,
        Status status,
        TransactionType transactionType,
        String timeStamp
) {
}
