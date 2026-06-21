package com.example.msa.model.DTO;

import com.example.msa.etc.Type;

public record CurrencyDTO(
        String symbol,
        String name,
        Double currentPriceUsd,
        Double marketCapUsd,
        Type type
) {
}
