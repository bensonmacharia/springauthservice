package com.bmacharia.springauthservice.service;

import com.bmacharia.springauthservice.model.ERole;
import com.bmacharia.springauthservice.model.Role;
import com.bmacharia.springauthservice.model.User;
import com.bmacharia.springauthservice.repository.RoleRepository;
import com.bmacharia.springauthservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     * User user = userRepository.findByUsername(username)
     * .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
     * <p>
     * return UserDetailsImpl.build(user);
     * }
     **/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found: {}", username);
        }

        return UserDetailsImpl.build(user);
    }

    @Override
    public List<User> listAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Role addRole(Role role) {
        log.info("Adding new role:- {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public User addUser(User user) {
        log.info("Adding new user:- {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User addRoleToUser(String username, ERole roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.getRoles().add(role);
        return user;
    }
}
