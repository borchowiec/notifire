package com.borchowiec.userservice.controller;

import com.borchowiec.userservice.model.User;
import com.borchowiec.userservice.model.UserRole;
import com.borchowiec.userservice.repository.UserRepository;
import com.borchowiec.userservice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping
public class ApiController {

    private UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public void addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
    }
}
