package com.example.wallet_api.repository;

import com.example.wallet_api.entity.Wallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends ReactiveMongoRepository<Wallet,String> {
}
