package com.lumitech.ecommerceapp.users.service.impl;

import com.lumitech.ecommerceapp.authorities.model.entity.Authorities;
import com.lumitech.ecommerceapp.authorities.service.AuthoritiesService;
import com.lumitech.ecommerceapp.users.exception.error.UserAlreadyExists;
import com.lumitech.ecommerceapp.users.model.dto.UserDTO;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.repository.UserRepository;
import com.lumitech.ecommerceapp.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthoritiesService authoritiesService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthoritiesService authoritiesService) {
        this.userRepository = userRepository;
        this.authoritiesService = authoritiesService;
    }

    @Override
    public User saveAndReturnUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User convertUserDtoToUser(UserDTO userDTO) {
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .address(userDTO.getAddress())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    /**
     * This method creates a new user and assign a role to it before saving it to the database
     * and returning it to the controller
     *
     * @param userDTO the userDTO object that contains the user information
     * @return the user object that was created
     */
    @Override
    public User createNewUserAssignRole(UserDTO userDTO) {
        User newUser = convertUserDtoToUser(userDTO);

        // Throw an exception if the user already exists in the database
        if (userAlreadyExists(newUser)){
            throw new UserAlreadyExists();
        }

        // Saving the user before sending it to the authorities service to assign a role to it
        userRepository.save(newUser);
        newUser = authoritiesService.assignRoleToUserAndSave(newUser);

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
    public boolean userAlreadyExists(User userToCheck) {
        return getAllUsers().stream()
                .anyMatch(user -> user.getEmail().equals(userToCheck.getEmail()));
    }
}
