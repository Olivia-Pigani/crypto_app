package com.example.user_api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;

    private String firstName;
    private String lastName;

    private String username;

    private String email;

    private String password;
}
