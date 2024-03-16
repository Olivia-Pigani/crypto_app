package com.example.wallet_api.service;

import com.example.wallet_api.dto.WalletDTO;
import com.example.wallet_api.entity.Wallet;
import com.example.wallet_api.repository.WalletRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    //CRUD

    public Mono<Wallet> getWalletById(String id) {
        return walletRepository.findById(id);
    }

    public Flux<Wallet> getAll() {
        return walletRepository.findAll();
    }

    public Mono<Void> deleteWallet(String walletId) {
        return walletRepository.deleteById(walletId);
    }

    public Mono<Wallet> addWallet(WalletDTO walletDTO) {

        Wallet newWallet = Wallet.builder()
                .balance(0.00)
                .userId(walletDTO.getUserId())
                .build();

       return walletRepository.save(newWallet);
    }


//    public Mono<Wallet> updateWalletQuantity(Wallet updatedWallet, int cryptoQuantity){
//        return walletRepository.findById(updatedWallet.getId())
//                .flatMap(wallet -> )
//    }


}
