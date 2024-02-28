package com.example.market_api.controller;

import com.example.market_api.entity.MarketData;
import com.example.market_api.repository.MarketDataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Random;

@RequestMapping("marketData")
@RestController
public class MarketDataAPIController {

private final Random random;

private final MarketDataRepository marketDataRepository;
    private LocalDateTime currentTradingTime = LocalDateTime.now();
    public MarketDataAPIController(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;

        random = new Random();
    }

    @PostMapping()
    public Mono<MarketData> addMarketData(@RequestBody MarketData marketData) {
        marketData.setTradingTime(currentTradingTime);
        currentTradingTime = currentTradingTime.plusDays(1);
        double cryptoValue = generateCryptoValue(marketData.getCrypto().getId());
        marketData.setCryptoValue(cryptoValue);
        return marketDataRepository.save(marketData);
    }
    private double generateCryptoValue(String cryptoId) {
        return Math.random() * 1000;

}

    @GetMapping("/marketData/{cryptoId}")
    public Flux<MarketData> getMarketData(@PathVariable String cryptoId) {
       return marketDataRepository.findAllById(cryptoId);
    }
}

