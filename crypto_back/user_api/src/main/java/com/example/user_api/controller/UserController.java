package com.example.user_api.controller;

import com.example.user_api.entity.User;
import com.example.user_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private  final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> create(@Valid @RequestBody User user) {
        System.out.println("post user is ok");
        return userService.createUser(user);
    }

    @GetMapping("/{username}")
    public Mono<ResponseEntity<User>> getUserByUsername(@PathVariable String username) {
        Mono<User> user = userService.getUserByUsername(username);
        return user.map(u -> ResponseEntity.ok(u)).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{email}")
    public Mono<ResponseEntity<User>> getUserByEmail(@PathVariable String email) {
        Mono<User> user = userService.getUserByEmail(email);
        return user.map(u -> ResponseEntity.ok(u)).defaultIfEmpty(ResponseEntity.notFound().build());
    }
    /*@PostMapping("/search/email")
    public Flux<User> fetchUsersByEmail(@RequestBody List<String> email) {
        return userService.fetchUsers(email);
    }*/


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        UserDetails userDetails = userService.authenticate(username, password);

        if (userDetails != null) {
            // L'authentification réussie, retournez les détails de l'utilisateur connecté
            return ResponseEntity.ok(userDetails);
        } else {
            // L'authentification a échoué, retournez un message d'erreur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiers invalid");
        }
    }
}
