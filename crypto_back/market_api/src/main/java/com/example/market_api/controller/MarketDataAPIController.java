package com.example.market_api.controller;

import com.example.market_api.repository.MarketDataRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RequestMapping("marketData")
@RestController
public class MarketDataController {

private final Random random;
private final MarketDataRepository marketDataRepository;

    public MarketDataController(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
        random = new Random();
    }




}
