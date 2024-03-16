package com.example.wallet_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WalletDTO {

    private double balance;

    private String userId;

}
