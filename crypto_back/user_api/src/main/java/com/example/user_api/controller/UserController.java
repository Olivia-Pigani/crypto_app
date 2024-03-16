package com.example.user_api.controller;

import com.example.user_api.dto.UserDTO;
import com.example.user_api.entity.User;
import com.example.user_api.service.UserService;
import com.example.user_api.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class UserController {

    private  final UserService userService;



    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/sign-up") // ADD
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> create(@Valid @RequestBody UserDTO newUserDto) {
        return userService.createUser(newUserDto)
                .doOnSuccess((Void)-> System.out.println( "new user has been produced"))
                .doOnError((Void)-> System.out.println("error while producing new user"));
    }

//    @GetMapping("/{username}")
//    public Mono<ResponseEntity<User>> getUserByUsername(@PathVariable String username) {
//        Mono<User> user = userService.getUserByUsername(username);
//        return user.map(u -> ResponseEntity.ok(u)).defaultIfEmpty(ResponseEntity.notFound().build());
//    }

    @GetMapping()
    public Flux<User> getAllUsers(){
        return userService.getAllUsers();
    }

//    @GetMapping("/{email}")
//    public Mono<ResponseEntity<User>> getUserByEmail(@PathVariable String email) {
//        Mono<User> user = userService.getUserByEmail(email);
//        return user.map(u -> ResponseEntity.ok(u)).defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//    @PostMapping("/search/email")
//    public Flux<User> fetchUsersByEmail(@RequestBody List<String> email) {
//        return userService.fetchUsers(email);
//    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
//        UserDetails userDetails = userService.authenticate(username, password);
//
//        if (userDetails != null) {
//            // L'authentification réussie, retournez les détails de l'utilisateur connecté
//            return ResponseEntity.ok(userDetails);
//        } else {
//            // L'authentification a échoué, retournez un message d'erreur
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiers invalid");
//        }
//    }


//
//
@PostMapping("/sign-in")
public Mono<ResponseEntity<Object>> signIn(@Valid @RequestBody UserDTO userDTO) {
    return userService.getUserWithEmailAndPassword(userDTO)
            .map(user -> {
                String jwt = JwtUtils.generateToken(user.getUsername());
                System.out.println("JWT: " + jwt);
                return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).build();
            })
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
}


}
