package com.lumitech.ecommerceapp.users.service;

import com.lumitech.ecommerceapp.common.dto.StatusMessage;
import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import com.lumitech.ecommerceapp.users.model.dto.UserNewPasswordDTO;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveAndReturnUser(User user);
    User convertRegisterRequestToUser(RegisterRequest registerRequest);
    User createNewUserAssignRoleAndCart(RegisterRequest registerRequest);
    void deleteAll();
    List<User> getAllUsers();
    Optional<User> findByEmail(String email);

    Optional<User> findById(long id);

    StatusMessage deleteUserById(long id);

    boolean userAlreadyExists(User userToCheck);

    void isUserCostumer(User user);

    void isUserAdmin(User user);

    void updateUserPassword(User user, String newPassword);

    boolean confirmOldPasswordIsCorrect(User user, String currentPassword);

    StatusMessage changeUserPassword(User user, UserNewPasswordDTO userNewPasswordDTO);
}
