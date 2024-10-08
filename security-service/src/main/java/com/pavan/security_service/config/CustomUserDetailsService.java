package com.pavan.security_service.config;

import com.pavan.security_service.entity.User;
import com.pavan.security_service.exception.InvalidUserException;
import com.pavan.security_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> credentials = repository.findByUsername(username);
        return credentials.map(CustomUserDetails::new).orElseThrow(() -> new InvalidUserException("User not found with username : " + username));
    }
}