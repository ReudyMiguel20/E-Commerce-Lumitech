package com.lumitech.ecommerceapp.auth.service.impl;

import com.lumitech.ecommerceapp.auth.model.dto.AuthenticationResponse;
import com.lumitech.ecommerceapp.auth.service.AuthService;
import com.lumitech.ecommerceapp.common.jwt.JwtService;
import com.lumitech.ecommerceapp.users.exception.error.EmailNotFoundException;
import com.lumitech.ecommerceapp.users.model.dto.AuthenticationRequest;
import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User newUser = userService.createNewUserAssignRoleAndCart(request);

        String jwtToken = jwtService.generateToken(newUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword())
        );
        User user = userService.findByEmail(request.getEmail()).orElseThrow(EmailNotFoundException::new);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    
}
