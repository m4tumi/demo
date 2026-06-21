package com.example.msa.model.entity;

import com.example.msa.etc.WalletType;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;

    @Property("address")
    private String address;

    @Property("balance")
    private Double balance;

    @Property("walletType")
    private WalletType walletType;

    @Property("isActive")
    private Boolean isActive;

    @Relationship(type="HOLDS",direction = Relationship.Direction.OUTGOING)
    private Currency currency;

    public Wallet(String address, Double balance, WalletType walletType, Boolean isActive) {
        this.address = address;
        this.balance = balance;
        this.walletType = walletType;
        this.isActive = isActive;
    }
}
