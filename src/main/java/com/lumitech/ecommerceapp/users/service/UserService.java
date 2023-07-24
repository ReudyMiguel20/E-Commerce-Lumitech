package com.lumitech.ecommerceapp.users.service;

import com.lumitech.ecommerceapp.users.model.dto.AuthenticationRequest;
import com.lumitech.ecommerceapp.authorities.model.dto.AuthenticationResponse;
import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;

public interface UserService {

    User saveAndReturnUser(User user);
    User convertRegisterRequestToUser(RegisterRequest registerRequest);
    AuthenticationResponse createNewUserAssignRole(RegisterRequest registerRequest);
    void deleteAll();
    List<User> getAllUsers();
    boolean userAlreadyExists(User userToCheck);

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
