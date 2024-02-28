package com.example.market_api.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "marketData")
public class MarketData {

    @Id
    private int id;
    private LocalDateTime tradingTime;
    private double performance;
    @DBRef
    private Crypto crypto;

}
