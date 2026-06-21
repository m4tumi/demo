package com.example.msa.model.entity;

import com.example.msa.etc.KycStatus;
import com.example.msa.etc.TierLevel;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Property("username")
    private String username;

    @Property("email")
    private String email;

    @Property("kycStatus")
    private KycStatus kycStatus;

    @Property("county")
    private String county;

    @Property("tierLevel")
    private TierLevel tierLevel;

   @Relationship(type="OWNS",direction = Relationship.Direction.OUTGOING)
   private Set<Wallet> wallets=new HashSet<>();

   // @Relationship(type = "REFERRED_BY", direction =Relationship.Direction.OUTGOING)
   // private String referencedBy;



    public User(String username, String email, KycStatus kycStatus, String county, TierLevel tierLevel) {
        this.username = username;
        this.email = email;
        this.kycStatus = kycStatus;
        this.county = county;
        this.tierLevel = tierLevel;
    }
}
