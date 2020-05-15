package com.borchowiec.userservice.controller;

import com.borchowiec.userservice.model.User;
import com.borchowiec.userservice.repository.UserRepository;
import com.borchowiec.userservice.security.CustomUserDetailsService;
import com.borchowiec.userservice.security.JwtTokenProvider;
import com.borchowiec.userservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @Test
    void addUser_usernameAlreadyTaken_shouldReturn400() throws Exception {
        User user = new User(null, "username", "passs", "email@mail.com", null);

        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        mockMvc.perform(post("/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUser_emailAlreadyTaken_shouldReturn400() throws Exception {
        User user = new User(null, "username", "passs", "email@mail.com", null);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        mockMvc.perform(post("/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUser_properData_shouldReturn200() throws Exception {
        User user = new User(null, "username", "passs", "email@mail.com", null);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        mockMvc.perform(post("/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }
}