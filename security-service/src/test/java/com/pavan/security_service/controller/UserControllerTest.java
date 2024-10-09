package com.pavan.security_service.controller;

import com.pavan.security_service.dto.UserDTO;
import com.pavan.security_service.entity.User;
import com.pavan.security_service.exception.InvalidTokenException;
import com.pavan.security_service.exception.InvalidUserDetails;
import com.pavan.security_service.exception.InvalidUserException;
import com.pavan.security_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNewUser_Success() {
        User user = new User();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.saveUser(user)).thenReturn("User registered successfully");

        String response = userController.addNewUser(user, bindingResult);
        assertEquals("User registered successfully", response);
    }

    @Test
    void testAddNewUser_ValidationError() {
        User userCredential = new User();

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("userCredential", "name", "Name is required"),
                new FieldError("userCredential", "email", "Email format is invalid")
        ));

        InvalidUserDetails exception = assertThrows(InvalidUserDetails.class, () -> {
            userController.addNewUser(userCredential, bindingResult);
        });

        assertTrue(exception.getMessage().contains("Validation errors:"));
    }


    @Test
    void testGenerateUser_Success() {
        UserDTO userDTO = new UserDTO("username", "password");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userService.generateToken("username")).thenReturn("generatedToken");

        String token = userController.generateUser(userDTO);
        assertEquals("generatedToken", token);
    }

    @Test
    void testGenerateUser_InvalidCredentials() {
        UserDTO userDTO = new UserDTO("username", "password");

        when(authenticationManager.authenticate(any())).thenThrow(new InvalidUserException("Invalid User Credentials"));

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.generateUser(userDTO);
        });

        assertEquals("Invalid User Credentials", exception.getMessage());
    }

    @Test
    void testValidateToken_Valid() {
        String token = "validToken";

        doNothing().when(userService).validateToken(token);

        String response = userController.validateToken(token);
        assertEquals("Valid User", response);
    }

    @Test
    void testValidateToken_Invalid() {
        String token = "invalidToken";

        doThrow(new InvalidTokenException("Invalid Token")).when(userService).validateToken(token);

        InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
            userController.validateToken(token);
        });

        assertEquals("Invalid Token", exception.getMessage());
    }
    @Test
    void testAddNewUser_MissingField() {
        User user = new User();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("user", "password", "Password is required")
        ));

        InvalidUserDetails exception = assertThrows(InvalidUserDetails.class, () -> {
            userController.addNewUser(user, bindingResult);
        });

        assertTrue(exception.getMessage().contains("Validation errors:"));
    }

    @Test
    void testGenerateUser_InvalidAuthentication() {

        UserDTO userDTO = new UserDTO("user", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.generateUser(userDTO);
        });

        assertEquals("Invalid User Credentials, Check username and password correctly", exception.getMessage());
    }
    @Test
    void testGenerateUser_UnexpectedException() {
        UserDTO userDTO = new UserDTO("username", "password");
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Unexpected error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userController.generateUser(userDTO);
        });

        assertEquals("Unexpected error", exception.getMessage());
    }

}
