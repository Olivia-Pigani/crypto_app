package com.example.composition_user_wallet.service;

import com.example.composition_user_wallet.dto.WalletDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Service
public class WalletService {


    private final WebClient webClient;


    public WalletService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8084").build();
    }


    public Mono<WalletDTO[]> get(){
        return webClient.get().uri("/{walletId}").retrieve().bodyToMono(WalletDTO[].class);
    }


    public Mono<WalletDTO> addWalletForUser(Long userId){
        return webClient.post()
                .uri("/addwallet")
                .bodyValue(Map.of("userId",userId))
                .retrieve()
                .bodyToMono(WalletDTO.class);
    }


}
