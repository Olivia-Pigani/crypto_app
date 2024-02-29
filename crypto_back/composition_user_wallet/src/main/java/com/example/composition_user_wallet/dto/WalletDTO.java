package com.example.composition_user_wallet.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
public class WalletDTO {

    private Long id;

    private double balance;

}
