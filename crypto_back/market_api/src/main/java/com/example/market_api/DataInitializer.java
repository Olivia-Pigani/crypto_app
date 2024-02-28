package com.example.market_api;

import com.example.market_api.entity.Crypto;
import com.example.market_api.entity.MarketData;
import com.example.market_api.repository.CryptoRepository;
import com.example.market_api.repository.MarketDataRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements ApplicationRunner {


        private final MarketDataRepository marketDataRepository;
        private final CryptoRepository cryptoRepository;

        public DataInitializer(MarketDataRepository marketDataRepository, CryptoRepository cryptoRepository) {
            this.marketDataRepository = marketDataRepository;
            this.cryptoRepository = cryptoRepository;
        }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Générer aléatoirement des données pour MarketData et les sauvegarder dans la base de données
        Random random = new Random();
        Flux<Crypto> allCryptos = cryptoRepository.findAll(); // Récupérer toutes les cryptos disponibles

        // Générer des données aléatoires pour MarketData
        for (int i = 0; i < 10; i++) { // Générer 10 enregistrements de données de marché
            LocalDateTime tradingTime = LocalDateTime.now().minusDays(random.nextInt(30)); // Date aléatoire dans les 30 derniers jours
            double performance = random.nextDouble() * 100; // Performance aléatoire entre 0 et 100
            Crypto randomCrypto = allCryptos.collectList().block().get(random.nextInt(allCryptos.collectList().block().size()));  // Crypto aléatoire parmi celles disponibles

            MarketData marketData = MarketData.builder()
                    .tradingTime(tradingTime)
                    .performance(performance)
                    .crypto(randomCrypto)
                    .build();

            marketDataRepository.save(marketData);
        }
    }
}


