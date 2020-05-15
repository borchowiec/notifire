package com.borchowiec.userservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // todo get real user from database
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUsername("admin");
        userPrincipal.setPassword("password");
        return userPrincipal;
    }
}
