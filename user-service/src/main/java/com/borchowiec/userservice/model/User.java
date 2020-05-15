package com.borchowiec.userservice.model;

import com.borchowiec.userservice.annotation.UniqueEmail;
import com.borchowiec.userservice.annotation.UniqueUsername;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Document(collection = "Users")
public class User {
    @Id
    private String id;

    @UniqueUsername
    @Indexed(unique = true)
    @NotNull
    @Size(min = 4, max = 30)
    private String username;

    @NotNull
    @Size(min = 5)
    private String password;

    @UniqueEmail
    @Indexed(unique = true)
    @NotNull
    @Email
    private String email;

    private List<UserRole> roles;
}
