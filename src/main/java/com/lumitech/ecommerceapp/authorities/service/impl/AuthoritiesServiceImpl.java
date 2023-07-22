package com.lumitech.ecommerceapp.authorities.service.impl;

import com.lumitech.ecommerceapp.authorities.model.entity.Authorities;
import com.lumitech.ecommerceapp.authorities.repository.AuthoritiesRepository;
import com.lumitech.ecommerceapp.authorities.service.AuthoritiesService;
import com.lumitech.ecommerceapp.users.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

    private final AuthoritiesRepository authoritiesRepository;

    @Autowired
    public AuthoritiesServiceImpl(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    @Override
    public Authorities save(Authorities authority){
        return authoritiesRepository.save(authority);
    }

    /**
     * This method assigns a role to the user and saves it to the database
     * @param user the user object that is being created
     * @return send the user object with the role assigned back to the UserServiceImpl
     */
    @Override
    public User assignRoleToUserAndSave(User user) {
        Authorities roleForUser = decideRoleForUser(user);
        authoritiesRepository.save(roleForUser);

        // Create a list of authorities and assign it to the user
        List<Authorities> roleListForUser = new ArrayList<>();
        roleListForUser.add(roleForUser);
        user.setRoles(roleListForUser);

        return user;
    }


    /**
     * This method decides the role for the user based on the number of users in the database
     * @param user the user object that is being created
     * @return the authorities object that contains the role for the user
     */
    @Override
    public Authorities decideRoleForUser(User user) {
        if (getAllAuthorities().size() == 0) {
            return Authorities.builder()
                    .user(user)
                    .role(Authorities.Roles.ROLE_ADMIN)
                    .build();
        } else {
            return Authorities.builder()
                    .user(user)
                    .role(Authorities.Roles.ROLE_CUSTOMER)
                    .build();
        }
    }

    @Override
    public List<Authorities> getAllAuthorities() {
        return authoritiesRepository.findAll();
    }
}
