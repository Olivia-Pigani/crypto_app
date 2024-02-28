package com.example.market_api.controller;

import com.example.market_api.entity.Crypto;
import com.example.market_api.repository.CryptoRepository;
import com.example.market_api.repository.MarketDataRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Random;

@RequestMapping("crypto")
@RestController
public class CryptoAPIController {

    private final Random random;
    private final CryptoRepository cryptoRepository;


    public CryptoAPIController(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
        random = new Random();
    }

    @PostMapping
    Mono<Crypto> createCrypto(@RequestBody Crypto crypto) {
        return cryptoRepository.save(crypto);
    }
    @GetMapping(value = "{crypto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Crypto> get(@PathVariable String crypto) {
        return cryptoRepository.findCryptoByName(crypto);
    }
}
