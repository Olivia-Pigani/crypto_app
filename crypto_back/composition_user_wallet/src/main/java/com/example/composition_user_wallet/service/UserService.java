package com.example.composition_user_wallet.service;


import com.example.composition_user_wallet.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {


    private final WebClient webClient;

    public UserService() {
        webClient = WebClient.builder().baseUrl("http://localhost:8088/").build();
    }

    public Mono<UserDTO> getUserById(Long userId) {
        return webClient.get()
                .uri("/users/{userId}", userId)
                .retrieve().bodyToMono(UserDTO.class);
    }


}
