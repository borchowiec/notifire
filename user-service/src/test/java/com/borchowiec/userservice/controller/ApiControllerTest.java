package com.borchowiec.userservice.controller;

import com.borchowiec.userservice.model.User;
import com.borchowiec.userservice.model.UserRole;
import com.borchowiec.userservice.payload.UserInfoResponse;
import com.borchowiec.userservice.repository.UserRepository;
import com.borchowiec.userservice.security.CustomUserDetailsService;
import com.borchowiec.userservice.security.JwtTokenProvider;
import com.borchowiec.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(ApiController.class)
@TestPropertySource(locations="classpath:test.properties")
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

    @MockBean
    private AuthenticationManager authenticationManager;
    
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
        User user = new User(null, "username", "passsss", "email123@mail.com", null);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        mockMvc.perform(post("/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getUser_userDoesntExists_shouldReturn404() throws Exception {
        String userId = "1ljk2lk13j2l3kj12";
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/info/" + userId)).andExpect(status().isNotFound());
    }

    @Test
    void getUser_userExists_shouldReturn200AndUserOfGivenId() throws Exception {
        User user = new User("1ljk2lk13j2l3kj12", "usernamee", "passsword", "email@mail.com",
                Collections.singletonList(UserRole.USER));

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        MvcResult result = mockMvc
                .perform(get("/info/" + user.getId()))
                .andExpect(status().isOk())
                .andReturn();

        UserInfoResponse given = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(), UserInfoResponse.class);
        UserInfoResponse expected = new UserInfoResponse(user);
        assertEquals(expected, given);
    }
}