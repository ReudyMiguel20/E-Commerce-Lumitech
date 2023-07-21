package com.lumitech.ecommerceapp.users.service.impl;

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

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    @Override
    public User createAndSaveNewUser(UserDTO userDTO) {
        User newUser = convertUserDtoToUser(userDTO);

        //Logic here to check if the user already exists
        if (userAlreadyExists(newUser)){
            throw new UserAlreadyExists();
        }

        return this.userRepository.save(newUser);
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
