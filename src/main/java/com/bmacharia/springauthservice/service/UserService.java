package com.bmacharia.springauthservice.service;

import com.bmacharia.springauthservice.model.ERole;
import com.bmacharia.springauthservice.model.Role;
import com.bmacharia.springauthservice.model.User;

import java.util.List;

public interface UserService {
    List<User> listAllUsers();

    Role addRole(Role role);

    User addUser(User user);

    User addRoleToUser(String username, ERole roleName);
}
