package com.borchowiec.userservice.service;

import com.borchowiec.userservice.model.User;
import com.borchowiec.userservice.model.UserRole;
import com.borchowiec.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:test.properties")
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void addUser_properData_userShouldBeAddedWithHashedPassword() {
        // given
        String id = "someid";
        String username = "username";
        String password = "pass";
        String email = "email@mail.com";
        List<UserRole> roles = Collections.singletonList(UserRole.USER);
        User user = new User(id, username, password, email, null);

        // when
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        new UserService(userRepository, passwordEncoder).addUser(user);

        // then
        User given = userRepository.findById("someid").orElse(null);
        assertNotNull(given);
        assertEquals(id, given.getId());
        assertNotEquals(password, given.getPassword());
        assertEquals(email, given.getEmail());
        assertEquals(username, given.getUsername());
        assertEquals(roles, given.getRoles());
    }
}