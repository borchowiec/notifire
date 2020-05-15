package com.borchowiec.userservice.payload;

import com.borchowiec.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private String id;
    private String username;
    private String email;

    public UserInfoResponse(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
