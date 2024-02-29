package com.example.user_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trader {

    @Id
    private int id;

    private String firstName;
    private String lastName;

    private String username;

    private String email;

    private String password;


}
