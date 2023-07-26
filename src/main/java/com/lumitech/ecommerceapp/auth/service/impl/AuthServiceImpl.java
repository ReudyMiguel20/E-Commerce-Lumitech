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

//    public Authorities save(Authorities authority){
//        return authRepository.save(authority);
//    }

    /**
     * This method assigns a role to the user and saves it to the database
     * @param user the user object that is being created
     * @return send the user object with the role assigned back to the UserServiceImpl
     */
//    public User assignRoleToUserAndSave(User user) {
//        Authorities roleForUser = decideRoleForUser(user);
//        authRepository.save(roleForUser);
//
//        // Create a list of authorities and assign it to the user
////        List<Authorities> roleListForUser = new ArrayList<>();
////        roleListForUser.add(roleForUser);
////        user.setRoles(roleListForUser);
//
//        return user;
//    }


    /**
     * This method decides the role for the user based on the number of users in the database
     * @param user the user object that is being created
     * @return the authorities object that contains the role for the user
     */
//    public Authorities decideRoleForUser(User user) {
////        if (getAllAuthorities().size() == 0) {
////            return Authorities.builder()
////                    .user(user)
////                    .role(Authorities.Roles.ROLE_ADMIN)
////                    .build();
////        } else {
////            return Authorities.builder()
////                    .user(user)
////                    .role(Authorities.Roles.ROLE_CUSTOMER)
////                    .build();
////        }
//    }

//    @Override
//    public List<Authorities> getAllAuthorities() {
//        return authoritiesRepository.findAll();
//    }


}
