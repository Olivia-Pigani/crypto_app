package com.example.user_api.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(collection = "user")
public class User {

    @Id
    private long id;

    @NotBlank(message = "First name is mandatory")
    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @NotBlank(message = "Last name is mandatory")
    @NotNull(message = "Last name cannot be null")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 charaters ")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 8, max = 12, message = "password must be between 8 and 12 charaters ")
    private String password;



}
