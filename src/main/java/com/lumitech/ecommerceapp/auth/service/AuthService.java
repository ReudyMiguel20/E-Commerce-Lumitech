package com.lumitech.ecommerceapp.auth.service;

import com.lumitech.ecommerceapp.auth.model.dto.AuthenticationResponse;
import com.lumitech.ecommerceapp.users.model.dto.AuthenticationRequest;
import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import org.springframework.stereotype.Service;


public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
