package com.example.user_api.service;

import com.example.user_api.entity.User;
import com.example.user_api.repository.UserRepository;
import io.r2dbc.spi.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;


@Service
@Slf4j

public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConnectionFactory connectionFactory) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<User> createUser(User user) {
        Long id = (long) (Math.random() * 20000);
        user.setId(id);
        return userRepository.save(user);
    }


    public Flux<User> getAllUsers(){
        return userRepository.findAll();
    }


    public Mono<User> getUserByUsername(String username){
        return  userRepository.findByUsername(username);
    }

    public Mono<User> getUserByEmail(String email){
        return  userRepository.findByEmail(email);
    }
    public Mono<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    // Here the db lookup uses parallel processing
    public Flux<User> fetchUsers(List<String> userIds) {
        return Flux.fromIterable(userIds)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(i -> findById(Long.valueOf(i)))
                .ordered((u1, u2) -> Math.toIntExact(u2.getId() - u1.getId()));
    }




    public UserDetails authenticate(String username, String password) {
        // Récupérer le trader par nom d'utilisateur
        User user = userRepository.findByUsername(username).block();

        if (user != null && user.getPassword().equals(password)) {
            // Les informations d'identification sont valides, créer et retourner un UserDetails
            return loadUserByUsername(username);
        } else {
            // Les informations d'identification sont incorrectes, renvoyer null
            return null;
        }
    }
    @Override
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> {
                    if (user == null) {
                        throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
                    }
                    return new org.springframework.security.core.userdetails.User(
                            username,
                            "{noop}" + user.getPassword(),
                            true,
                            true,
                            true,
                            true,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                }).block(); // Bloquez le flux pour obtenir directement le UserDetails
    }



}
