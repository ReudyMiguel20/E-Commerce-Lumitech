package com.lumitech.ecommerceapp.authorities.service;

import com.lumitech.ecommerceapp.authorities.model.entity.Authorities;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;

public interface AuthoritiesService {

    Authorities save(Authorities authority);

    User assignRoleToUserAndSave(User user);

    Authorities decideRoleForUser(User user);

    List<Authorities> getAllAuthorities();
}
