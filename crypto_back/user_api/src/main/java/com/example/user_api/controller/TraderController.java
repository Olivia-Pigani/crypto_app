package com.example.user_api.controller;

import com.example.user_api.entity.Trader;
import com.example.user_api.service.TraderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class TraderController {

    private  final TraderService traderService;



    public TraderController(TraderService traderService, PasswordEncoder passwordEncoder) {
        this.traderService = traderService;

    }

    @PostMapping("/add")
    public Mono<Trader> post(@RequestBody Trader trader){
        System.out.println("post trader is ok");
        return traderService.add(trader);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        UserDetails userDetails = traderService.authenticate(username, password);

        if (userDetails != null) {
            // L'authentification réussie, retournez les détails de l'utilisateur connecté
            return ResponseEntity.ok(userDetails);
        } else {
            // L'authentification a échoué, retournez un message d'erreur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        }
    }
}
