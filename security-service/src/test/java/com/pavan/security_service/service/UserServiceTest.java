package com.pavan.security_service.service;

import com.pavan.security_service.entity.User;
import com.pavan.security_service.exception.UserAlreadyExists;
import com.pavan.security_service.repository.UserRepository;
import com.pavan.security_service.utils.Constant;
import com.pavan.security_service.utils.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("plainPassword");

        // Simulate user not existing in the repository
        when(repository.findByUsername("testUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        String response = userService.saveUser(user);

        assertEquals(Constant.USER_SAVED, response);
        verify(repository, times(1)).findByUsername("testUser");
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(repository, times(1)).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testSaveUser_UserAlreadyExists() {
        User existingUser = new User();
        existingUser.setUsername("testUser");

        // Simulate user already existing in the repository
        when(repository.findByUsername("testUser")).thenReturn(Optional.of(existingUser));

        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setPassword("plainPassword");

        // Expecting UserAlreadyExists exception
        assertThrows(UserAlreadyExists.class, () -> userService.saveUser(newUser));

        verify(repository, times(1)).findByUsername("testUser");
        verify(repository, never()).save(any(User.class)); // Save should never be called
        verify(passwordEncoder, never()).encode(anyString()); // Password encoding should never happen
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        String expectedToken = "generatedToken";

        when(jwtService.generateToken(username)).thenReturn(expectedToken);

        String token = userService.generateToken(username);

        assertEquals(expectedToken, token);
        verify(jwtService, times(1)).generateToken(username);
    }

    @Test
    void testValidateToken() {
        String token = "validToken";

        doNothing().when(jwtService).validateToken(token);

        userService.validateToken(token);

        verify(jwtService, times(1)).validateToken(token);
    }

}
