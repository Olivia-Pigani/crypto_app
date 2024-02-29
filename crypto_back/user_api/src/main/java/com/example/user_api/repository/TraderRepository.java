package com.example.user_api.repository;

import com.example.user_api.entity.Trader;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TraderRepository extends R2dbcRepository<Trader, Long> {
   Mono<Trader> findByUsername(final String username);
}
