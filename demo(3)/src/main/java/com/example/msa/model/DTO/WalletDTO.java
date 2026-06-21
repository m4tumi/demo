package com.example.msa.model.DTO;

import com.example.msa.etc.WalletType;

public record WalletDTO(
        String address,
        Double balance,
        WalletType walletType,
        Boolean isActive
) {}