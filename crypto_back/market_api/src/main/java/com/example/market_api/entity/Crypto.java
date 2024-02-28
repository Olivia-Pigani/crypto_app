package com.example.market_api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Column;

import java.lang.annotation.Documented;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "crypto")
public class Crypto {

    @Id
    private String id;
    private String name;
    private String ticker;
    private double cryptoValue;


    public Crypto(String name, double cryptoValue,String ticker) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.cryptoValue = cryptoValue;
        this.ticker = ticker;

    }

    public Crypto() {
    }
}
