package com.borchowiec.userservice.controller;

import com.borchowiec.userservice.exception.UserNotFoundException;
import com.borchowiec.userservice.model.User;
import com.borchowiec.userservice.payload.AuthenticateRequest;
import com.borchowiec.userservice.payload.JwtAuthenticationResponse;
import com.borchowiec.userservice.payload.UserInfoResponse;
import com.borchowiec.userservice.repository.UserRepository;
import com.borchowiec.userservice.security.JwtTokenProvider;
import com.borchowiec.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class ApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    public ApiController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    /**
     * Adds new user to database.
     * @param user User that will be added.
     */
    @PostMapping("/add")
    public void addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/info/{userId}")
    public UserInfoResponse getUser(@PathVariable String userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Not found user of id " + userId));
        return new UserInfoResponse(user);
    }
}
