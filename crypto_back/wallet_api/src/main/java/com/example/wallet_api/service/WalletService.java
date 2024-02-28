package com.example.wallet_api.service;

import com.example.wallet_api.repository.WalletRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WalletService {

private WalletRepository walletRepository;
private ConnectionFactory connectionFactory;

    public WalletService(WalletRepository walletRepository, ConnectionFactory connectionFactory) {
        this.walletRepository = walletRepository;
        this.connectionFactory = connectionFactory;
        makeWalletTable();
    }




    private void makeWalletTable(){
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        Mono<Void> monoTable = databaseClient.sql("CREATE TABLE IF NOT EXISTS wallet" +
                "(id bigint primary key auto_increment" +
                ")")
                .then();

        monoTable.subscribe();

    }













}
