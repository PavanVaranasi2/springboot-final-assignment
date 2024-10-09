package com.pavan.security_service.controller;


import com.pavan.security_service.dto.UserDTO;
import com.pavan.security_service.entity.User;
import com.pavan.security_service.exception.InvalidTokenException;
import com.pavan.security_service.exception.InvalidUserDetails;
import com.pavan.security_service.exception.InvalidUserException;
import com.pavan.security_service.service.UserService;
import com.pavan.security_service.utils.Constant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.AUTH)
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public  UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(Constant.REGISTER_URL)
    public String addNewUser(@Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:---> ");
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessage.append(error.getField()).append("-->  ").append(error.getDefaultMessage()).append(";   ")
            );
            throw new InvalidUserDetails(errorMessage.toString());
        }
        return userService.saveUser(user);
    }

    @PostMapping(Constant.TOKEN)
    public String generateUser(@RequestBody UserDTO userDTO){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );
            return userService.generateToken(userDTO.getUsername());

        } catch (AuthenticationException e) {
            throw new InvalidUserException("Invalid User Credentials, Check username and password correctly");
        }
    }

    @GetMapping(Constant.VALIDATE)
    public String validateToken(@RequestParam("token") String token){
        try{
            userService.validateToken(token);
            return "Valid User";
        }
        catch (Exception e){
            throw new InvalidTokenException("Invalid Token");
        }
    }
}
