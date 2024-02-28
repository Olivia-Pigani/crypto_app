package com.example.market_api.repository;

import com.example.market_api.entity.Crypto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CryptoRepository extends ReactiveMongoRepository<Crypto, Integer> {

Mono<Crypto> findCryptoByName(String crypto);
}




/*
{
        "crypto": {
        "id": "65dfa4713a8e0844d18ba79b",
        "name": "Ethereum",
        "symbol": "ETH"
        }
        }
        {
        "crypto": {
        "id": "65dfa48b3a8e0844d18ba79c",
        "name": "Bitcoin",
        "symbol": "BTC"
        }
        }*/


