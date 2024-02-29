package com.example.composition_user_wallet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserWalletDTO {

    private Long userID;

    private Long walletId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;



    private double balance;





}
