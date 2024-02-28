package com.example.market_api.controller;

import com.example.market_api.entity.Crypto;
import com.example.market_api.repository.CryptoRepository;
import com.example.market_api.repository.MarketDataRepository;
import com.example.market_api.service.CryptoService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("crypto")
@RestController
public class CryptoAPIController {

    private final CryptoService cryptoService;
    private final CryptoRepository cryptoRepository;

    public CryptoAPIController(CryptoService cryptoService, CryptoRepository cryptoRepository) {
        this.cryptoService = cryptoService;
        this.cryptoRepository = cryptoRepository;
    }

    @PostConstruct
    public void insertDataOnStartup() {
        System.out.println("hello");
cryptoService.insertData();
    }

    @GetMapping("/all")
    public Flux<Crypto> getAllCrypto() {
        return cryptoRepository.findAll();
    }

    @PostMapping
    public Mono<Crypto> add(@RequestBody Crypto crypto) {
        return cryptoRepository.save(crypto)
                .doOnSuccess(savedCrypto -> cryptoService.insertData());
    }
}
