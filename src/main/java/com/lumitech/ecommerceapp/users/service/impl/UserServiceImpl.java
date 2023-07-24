package com.lumitech.ecommerceapp.users.service.impl;

import com.lumitech.ecommerceapp.auth.model.dto.AuthenticationResponse;
import com.lumitech.ecommerceapp.common.jwt.JwtService;
import com.lumitech.ecommerceapp.users.exception.error.EmailNotFoundException;
import com.lumitech.ecommerceapp.users.exception.error.UserAlreadyExistsException;
import com.lumitech.ecommerceapp.users.model.dto.AuthenticationRequest;
import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import com.lumitech.ecommerceapp.users.model.entity.Role;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.repository.UserRepository;
import com.lumitech.ecommerceapp.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
     * This method creates a new user and assign a role to it before saving it to the database
     * and returning it to the controller
     *
     * @param registerRequest the userDTO object that contains the user information
     * @return the user object that was created
     */
    @Override
    public User createNewUserAssignRole(RegisterRequest registerRequest) {
        User newUser = convertRegisterRequestToUser(registerRequest);

        // Throw an exception if the user already exists in the database
        if (userAlreadyExists(newUser)){
            throw new UserAlreadyExistsException();
        }

        // Saving the user before sending it to the authorities service to assign a role to it
        newUser = decideRoleForUser(newUser);
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
                .orElseThrow(EmailNotFoundException::new));
    }

    @Override
    public boolean userAlreadyExists(User userToCheck) {
        return getAllUsers().stream()
                .anyMatch(user -> user.getEmail().equals(userToCheck.getEmail()));
    }

    public User decideRoleForUser(User user) {
        if (getAllUsers().size() == 0) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        return user;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(EmailNotFoundException::new);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
