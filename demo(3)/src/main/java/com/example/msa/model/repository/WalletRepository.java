package com.example.msa.model.repository;

import com.example.msa.model.entity.Wallet;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface WalletRepository extends Neo4jRepository<Wallet,Long> {
    Optional<Wallet> findByAddress(String address);
    List<Wallet> findAllByWalletType(String walletType);
    List<Wallet> findAllByIsActive(String isActive);

    @Query("MATCH (u:User)-[:OWNS]->(w:Wallet) WHERE u.username = $username RETURN w")
    List<Wallet> findWalletsOf(@Param("username") String username);

}
