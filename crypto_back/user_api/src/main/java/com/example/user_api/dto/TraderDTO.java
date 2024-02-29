package com.example.user_api.dto;

import lombok.Data;

@Data
public class TraderDTO {
    private int id;

    private String firstName;
    private String lastName;

    private String username;

    private String email;

    private String password;
}
