package com.market.trading.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.market.trading.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data //to retrieve getter setter from lombok

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //id auto generate hogi everytime
    private Long id;

    private String fUllName;
    private String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Embedded
    private TwoFactorAuth twofactorAuth= new TwoFactorAuth();

    private USER_ROLE role= USER_ROLE.ROLE_CUSTOMER;

}
