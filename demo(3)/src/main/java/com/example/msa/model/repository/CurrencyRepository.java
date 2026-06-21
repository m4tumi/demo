package com.example.msa.model.repository;

import com.example.msa.model.entity.Currency;
import com.example.msa.model.entity.Transaction;
import com.example.msa.model.entity.Wallet;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CurrencyRepository {
    Optional<Currency> findBySymbol(String symbol);
    List<Currency> findByType(String type);

    @Query("MATCH (t:Transaction)-[:FROM_WALLET]->(w:Wallet)-[HOLDS]->(c:Currency) WHERE c.symbol = $symbol RETUREN t")
    List<Transaction> findTransactionByCurrency(@Param("symbol") String symbol);

    @Query("MATCH (t:Transaction)-[:FROM_WALLET]->(w:Wallet)<-[:OWNS]-(u:User) WHERE u.username = $username RETURN t")
    List<Transaction> findTransactionByUser(@Param("username") String username);


}
