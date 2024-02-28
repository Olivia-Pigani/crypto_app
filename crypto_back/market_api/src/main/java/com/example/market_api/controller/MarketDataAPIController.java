package com.example.market_api.controller;

import com.example.market_api.DataInitializer;
import com.example.market_api.entity.Crypto;
import com.example.market_api.entity.MarketData;
import com.example.market_api.repository.MarketDataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Random;

@RequestMapping("marketData")
@RestController
public class MarketDataAPIController {

private final Random random;
private DataInitializer dataInitializer;
private final MarketDataRepository marketDataRepository;

    public MarketDataAPIController(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
        random = new Random();
    }


    @PostConstruct
    public void initData() {
        dataInitializer.run();
    }


}
