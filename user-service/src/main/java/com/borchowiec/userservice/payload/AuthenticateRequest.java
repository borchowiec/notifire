package com.borchowiec.userservice.payload;

import lombok.Data;

@Data
public class AuthenticateRequest {
    private String usernameOrEmail;
    private String password;
}
