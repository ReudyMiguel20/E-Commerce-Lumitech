package com.lumitech.ecommerceapp.users.controller;

import com.lumitech.ecommerceapp.users.model.dto.AuthenticationRequest;
import com.lumitech.ecommerceapp.authorities.model.dto.AuthenticationResponse;
import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import com.lumitech.ecommerceapp.users.model.dto.RegistrationResponse;
import com.lumitech.ecommerceapp.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registeringNewUser(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authResponse = userService.createNewUserAssignRole(registerRequest);
        RegistrationResponse registrationResponse =
        return ResponseEntity.ok().body(authResponse);
    }

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(userService.register(request));
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}
