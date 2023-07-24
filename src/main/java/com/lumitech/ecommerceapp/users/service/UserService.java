package com.lumitech.ecommerceapp.users.service;

import com.lumitech.ecommerceapp.auth.model.dto.AuthenticationResponse;
import com.lumitech.ecommerceapp.users.model.dto.AuthenticationRequest;
import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveAndReturnUser(User user);
    User convertRegisterRequestToUser(RegisterRequest registerRequest);
    User createNewUserAssignRole(RegisterRequest registerRequest);
    void deleteAll();
    List<User> getAllUsers();
    Optional<User> findByEmail(String email);
    boolean userAlreadyExists(User userToCheck);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
