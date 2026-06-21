package com.example.msa.model.DTO;

import com.example.msa.etc.KycStatus;
import com.example.msa.etc.TierLevel;

public record UserDTO(
            String username,
            String email,
            KycStatus kycStatus,
            String county,
            TierLevel tierLevel
    ) {}

