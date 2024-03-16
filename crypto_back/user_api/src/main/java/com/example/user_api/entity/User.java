package com.example.user_api.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters ")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    private String email;

    //    @Size(min = 8, max = 12, message = "password must be between 8 and 12 charaters ")
    //    private String password;

    // the clear "password" is replaced by a hashed password system
    @Field(name = "hashed_password")
    @NotNull
    private byte[] hashedPassword;

    @NotNull
    private byte[] salt;


}
