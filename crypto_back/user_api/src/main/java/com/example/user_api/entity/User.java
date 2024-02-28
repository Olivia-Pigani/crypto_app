package com.example.user_api.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters long please")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters please")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid please")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters long please")
    private String password;



}
