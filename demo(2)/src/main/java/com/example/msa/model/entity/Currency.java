package com.example.msa.model.entity;

import com.example.msa.etc.Type;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Currency {
    @Id
    @GeneratedValue
    private Long id;

    @Property("symbol")
    private String symbol;

    @Property("name")
    private String name;

    @Property("currentPriceUsd")
    private Double currentPriceUsd;

    @Property("marketCapUsd")
    private Double marketCapUsd;

    @Property("type")
    private Type type;

    public Currency(String symbol, String name, Double currentPriceUsd, Double marketCapUsd, Type type) {
        this.symbol = symbol;
        this.name = name;
        this.currentPriceUsd = currentPriceUsd;
        this.marketCapUsd = marketCapUsd;
        this.type = type;
    }
}
