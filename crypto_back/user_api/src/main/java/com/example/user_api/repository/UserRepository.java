package com.example.user_api.repository;

import com.example.user_api.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

   Mono<User> findByUsername(final String username);
   Mono<User> findByEmail(final String email);

}
