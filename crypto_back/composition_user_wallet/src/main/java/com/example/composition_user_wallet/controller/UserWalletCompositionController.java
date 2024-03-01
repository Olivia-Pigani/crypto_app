package com.example.composition_user_wallet.controller;


import com.example.composition_user_wallet.dto.UserWalletDTO;
import com.example.composition_user_wallet.service.UserService;
import com.example.composition_user_wallet.service.WalletService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user_wallet")
public class UserWalletCompositionController {

    private UserService userService;
    private WalletService walletService;

    public UserWalletCompositionController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }


    @PostMapping("/addwallet/{userId}")
    public Mono<UserWalletDTO> addWallet(@PathVariable Long userId){
        return userService.getUserById(userId)
                .flatMap(user -> walletService.addWalletForUser(userId)
                        .map(walletDTO -> UserWalletDTO.builder()
                                .userID(user.getId())
                                .walletId(walletDTO.getId())
                                .balance(walletDTO.getBalance())
                                .build()));
    }
}
