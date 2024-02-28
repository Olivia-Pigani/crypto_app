package com.example.market_api.repository;

import com.example.market_api.entity.Crypto;
import com.example.market_api.entity.MarketData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MarketDataRepository extends ReactiveMongoRepository<MarketData, Integer> {


}
