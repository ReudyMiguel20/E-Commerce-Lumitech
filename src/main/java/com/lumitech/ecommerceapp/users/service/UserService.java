package com.lumitech.ecommerceapp.users.service;

import com.lumitech.ecommerceapp.users.model.dto.UserDTO;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;

public interface UserService {

    User saveAndReturnUser(User user);
    User convertUserDtoToUser(UserDTO userDTO);
    User createNewUserAssignRole(UserDTO userDTO);
    void deleteAll();
    List<User> getAllUsers();
    boolean userAlreadyExists(User userToCheck);
}
