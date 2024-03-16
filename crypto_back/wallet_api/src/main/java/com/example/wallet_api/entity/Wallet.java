package com.example.wallet_api.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@Builder
@Document("wallet")
public class Wallet {


    @Id
    private String id;

    private double balance;

    @Field("user_id")
    private String userId;



}
