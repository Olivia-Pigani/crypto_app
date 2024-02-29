package com.example.composition_wallet_market.Controller;

import com.example.composition_wallet_market.service.CryptoService;
import com.example.composition_wallet_market.service.WalletService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CompositionController {

private final WalletService walletService;
private final CryptoService cryptoService;


    public CompositionController(WalletService walletService, CryptoService cryptoService) {
        this.walletService = walletService;
        this.cryptoService = cryptoService;
    }




}
