package com.example.market_api.service;

import com.example.market_api.entity.Crypto;
import com.example.market_api.entity.MarketData;
import com.example.market_api.entity.PerformanceResult;
import com.example.market_api.repository.CryptoRepository;
import com.example.market_api.repository.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CryptoService {

    @Autowired
    private final MarketDataRepository marketDataRepository;

    @Autowired
    private final CryptoRepository cryptoRepository;
//

    public CryptoService(CryptoRepository cryptoRepository, MarketDataRepository marketDataRepository, CryptoRepository cryptoRepository1) {
        this.marketDataRepository = marketDataRepository;
        this.cryptoRepository = cryptoRepository1;
    }

//    public Mono<Double> calculatePerformanceForDate(String date, String idCrypto) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        LocalDateTime dateConverted = LocalDateTime.parse(date, formatter);
//
//
//        Mono<MarketData> nearestPriceMono = marketDataRepository.findFirstByCryptoIdAndTradingTimeGreaterThanEqualOrderByTradingTimeAsc(idCrypto, dateConverted)
//                .doOnNext(nearestPrice -> {
//                    System.out.println("Nearest price found: " + nearestPrice);
//                })
//                .onErrorResume(error -> {
//                    System.err.println("Error fetching nearest price: " + error.getMessage());
//                    return Mono.empty();
//                });
//        Mono<MarketData> latestPriceMono = marketDataRepository.findFirstByCryptoIdAndTradingTimeGreaterThanEqualOrderByTradingTimeAsc(idCrypto, dateConverted.minusDays(1))
//                .doOnNext(nearestPrice -> {
//                    System.out.println("Latest price found: " + nearestPrice);
//                })
//                .onErrorResume(error -> {
//                    System.err.println("Error fetching nearest price: " + error.getMessage());
//                    return Mono.empty();
//                });
//        Mono<Double> performanceMono = Mono.zip(nearestPriceMono, latestPriceMono)
//                .map(tuple -> {
//                    MarketData nearestPrice = tuple.getT1();
//                    MarketData latestPrice = tuple.getT2();
//                    if (nearestPrice != null && latestPrice != null) {
//                        double performance = ((latestPrice.getCryptoValue() - nearestPrice.getCryptoValue()) / nearestPrice.getCryptoValue()) * 100.0;
//                        System.out.println("Performance calculated: " + performance);
//                        return performance;
//                    } else {
//                        System.err.println("Error: One or both prices are null.");
//                        return 0.0;
//                    }
//                });
//
//
//        return performanceMono;
//
//    }

    public Flux<PerformanceResult> calculatePerformanceForDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        LocalDateTime dateConverted = LocalDateTime.parse(date, formatter);

        Flux<String> cryptoIdsFlux = marketDataRepository.findAll().map(MarketData::getCryptoId).distinct();

        return cryptoIdsFlux.flatMap(idCrypto ->
                calculatePerformanceForCrypto(idCrypto, dateConverted)
                        .map(performance -> new PerformanceResult(idCrypto, performance))
        );
    }

    private Mono<Double> calculatePerformanceForCrypto(String idCrypto, LocalDateTime dateConverted) {
        Mono<MarketData> nearestPriceMono = marketDataRepository.findFirstByCryptoIdAndTradingTimeGreaterThanEqualOrderByTradingTimeAsc(idCrypto, dateConverted)
                .onErrorResume(error -> {
                    System.err.println("Error fetching nearest price for crypto " + idCrypto + ": " + error.getMessage());
                    return Mono.empty();
                });

        Mono<MarketData> latestPriceMono = marketDataRepository.findFirstByCryptoIdAndTradingTimeGreaterThanEqualOrderByTradingTimeAsc(idCrypto, dateConverted.minusDays(1))
                .onErrorResume(error -> {
                    System.err.println("Error fetching latest price for crypto " + idCrypto + ": " + error.getMessage());
                    return Mono.empty();
                });

        return Mono.zip(nearestPriceMono, latestPriceMono)
                .map(tuple -> {
                    MarketData nearestPrice = tuple.getT1();
                    MarketData latestPrice = tuple.getT2();
                    if (nearestPrice != null && latestPrice != null) {
                        double performance = ((latestPrice.getCryptoValue() - nearestPrice.getCryptoValue()) / nearestPrice.getCryptoValue()) * 100.0;
                        System.out.println("Performance calculated for crypto " + idCrypto + ": " + performance);
                        return performance;
                    } else {
                        System.err.println("Error: One or both prices are null for crypto " + idCrypto);
                        return 0.0;
                    }
                });
    }
}





