package com.example.wallet_api.controller;


import com.example.wallet_api.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wallet")
public class WalletController {


// post pour achat, delete une quantity, get all et id

    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @GetMapping()




}
