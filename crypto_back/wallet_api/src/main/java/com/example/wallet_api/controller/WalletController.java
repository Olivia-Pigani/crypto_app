package com.example.wallet_api.controller;


import com.example.wallet_api.dto.WalletDTO;
import com.example.wallet_api.entity.Wallet;
import com.example.wallet_api.service.WalletService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("wallet")
public class WalletController {


    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @GetMapping("/{walletId}")
    public Mono<Wallet> getWalletById(@PathVariable("walletId") String walletId){
        return walletService.getWalletById(walletId);
    }

    @GetMapping()
    public Flux<Wallet> getAllWallet(){
        return walletService.getAll();
    }

    @DeleteMapping("/{walletId}")
    public Mono<Void> deleteAWallet(@PathVariable("walletId") String walletId){
        return walletService.deleteWallet(walletId);
    }


    @PostMapping("/add")
    public Mono<Wallet> addwallet(@RequestBody WalletDTO walletDTO){
        return walletService.addWallet(walletDTO);


    }




}
