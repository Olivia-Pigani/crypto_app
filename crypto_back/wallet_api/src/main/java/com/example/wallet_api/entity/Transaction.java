package com.example.wallet_api.entity;

// wallet + crypo relationship


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("transaction")
public class Transaction {

    @Id
    private long id;

    @Column("wallet_id")
    private Long walletId;

    @Column("crypto_id")
    private Long cryptoId;


}
