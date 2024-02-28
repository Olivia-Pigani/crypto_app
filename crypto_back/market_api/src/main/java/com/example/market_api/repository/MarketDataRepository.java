package com.example.market_api.repository;

import com.example.market_api.entity.Crypto;
import com.example.market_api.entity.MarketData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MarketDataRepository extends ReactiveMongoRepository<MarketData, Integer> {

    Flux<MarketData> findAllById(String id);

}
