package com.borchowiec.userservice.service;

import com.borchowiec.userservice.model.User;
import com.borchowiec.userservice.model.UserRole;
import com.borchowiec.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Adds new user to database. Before adding, sets up user's {@link UserRole Role} and encode password.
     * @param user User that will be added.
     */
    public void addUser(User user) {
        user.setRoles(Collections.singletonList(UserRole.USER));
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
