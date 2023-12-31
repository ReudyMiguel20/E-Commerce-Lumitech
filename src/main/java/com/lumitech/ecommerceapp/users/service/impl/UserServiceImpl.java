package com.lumitech.ecommerceapp.users.service.impl;

import com.lumitech.ecommerceapp.cart.service.CartService;
import com.lumitech.ecommerceapp.common.dto.StatusMessage;
import com.lumitech.ecommerceapp.users.exception.error.UserAlreadyExistsException;
import com.lumitech.ecommerceapp.users.exception.error.UserNotACustomerException;
import com.lumitech.ecommerceapp.users.exception.error.UserNotAnAdminException;
import com.lumitech.ecommerceapp.users.exception.error.UserNotFoundException;
import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import com.lumitech.ecommerceapp.users.model.dto.UserNewPasswordDTO;
import com.lumitech.ecommerceapp.users.model.entity.Role;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.repository.UserRepository;
import com.lumitech.ecommerceapp.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveAndReturnUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User convertRegisterRequestToUser(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .address(registerRequest.getAddress())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
    }

    /**
     * This method creates a new user and assign a role and a cart to it before saving it to the database
     * and returning it
     *
     * @param registerRequest the userDTO object that contains the user information
     * @return the user object that was created
     */
    @Override
    public User createNewUserAssignRoleAndCart(RegisterRequest registerRequest) {
        User newUser = convertRegisterRequestToUser(registerRequest);

        // Throw an exception if the user already exists in the database
        if (userAlreadyExists(newUser)) {
            throw new UserAlreadyExistsException();
        }

        // Saving the user before sending it to the authorities service to assign a role to it
        newUser = decideRoleForUser(newUser);
        newUser = saveAndReturnUser(newUser);
        newUser = cartService.createCartAndAssignToUser(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public void deleteAll() {
        this.userRepository.deleteAll();
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public StatusMessage deleteUserById(long id) {

        findById(id);

        userRepository.deleteById(id);

        return new StatusMessage().builder()
                .message("User has been deleted")
                .build();
    }

    @Override
    public boolean userAlreadyExists(User userToCheck) {
        return getAllUsers().stream()
                .anyMatch(user -> user.getEmail().equals(userToCheck.getEmail()));
    }

    /**
     * Assigning admin role to the first user that registers to the application
     * and customer role to the rest of the users
     *
     * @param user the user object that will be assigned a role
     * @return the user object with the assigned role
     */
    public User decideRoleForUser(User user) {
        if (getAllUsers().size() == 0) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.CUSTOMER);
        }
        return user;
    }

    @Override
    public void isUserCostumer(User user) {
        if (!user.getRole().equals(Role.CUSTOMER)) {
            throw new UserNotACustomerException();
        }
    }

    @Override
    public void isUserAdmin(User user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new UserNotAnAdminException();
        }
    }

    @Override
    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        saveAndReturnUser(user);
    }

    @Override
    public boolean confirmOldPasswordIsCorrect(User user, String currentPassword) {
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }

    @Override
    public StatusMessage changeUserPassword(User user, UserNewPasswordDTO userNewPasswordDTO) {

        if (confirmOldPasswordIsCorrect(user, userNewPasswordDTO.getCurrentPassword())) {
            updateUserPassword(user, userNewPasswordDTO.getNewPassword());

            return new StatusMessage("Password updated successfully");
        } else {
            return new StatusMessage("Current password does not match");
        }

    }


}
