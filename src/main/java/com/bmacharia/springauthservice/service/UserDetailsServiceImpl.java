package com.bmacharia.springauthservice.service;

import com.bmacharia.springauthservice.model.User;
import com.bmacharia.springauthservice.repository.RoleRepository;
import com.bmacharia.springauthservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class UserDetailsServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

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
}
