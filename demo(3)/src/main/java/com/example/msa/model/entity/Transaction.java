package com.example.msa.model.entity;

import com.example.msa.etc.Status;
import com.example.msa.etc.TransactionType;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

@Node("Transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction  {
    @Id
    @GeneratedValue
    private Long id;

    @Property("txHash")
    private String txHash;

    @Property("amount")
    private Double amount;

    @Property("feeUsd")
    private Double feeUsd;

    @Property("status")
    private Status status;

    @Property("transactionType")
    private TransactionType transactionType;

    @Property("timeStamp")
    private String timeStamp;

    @Relationship(type = "FROM_WALLET", direction = Relationship.Direction.OUTGOING)
    private Wallet fromWallet;

    @Relationship(type = "TO_WALLET", direction = Relationship.Direction.OUTGOING)
    private Wallet toWallet;

    @Relationship(type = "INVOLVES",direction = Relationship.Direction.OUTGOING)
    private Currency currency;

    public Transaction(String txHash, Double amount, Double feeUsd, Status status,
                       TransactionType transactionType, String timeStamp, Wallet fromWallet, Wallet toWallet, Currency currency) {
        this.txHash = txHash;
        this.amount = amount;
        this.feeUsd = feeUsd;
        this.status = status;
        this.transactionType = transactionType;
        this.timeStamp = timeStamp;
        this.fromWallet = fromWallet;
        this.toWallet = toWallet;
        this.currency = currency;
    }
}
