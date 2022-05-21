package com.bmacharia.springauthservice;

import com.bmacharia.springauthservice.model.Role;
import com.bmacharia.springauthservice.model.User;
import com.bmacharia.springauthservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.bmacharia.springauthservice.model.ERole.*;

@SpringBootApplication
public class SpringauthserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringauthserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.addRole(new Role(null, ROLE_VISITOR));
            userService.addRole(new Role(null, ROLE_USER));
            userService.addRole(new Role(null, ROLE_MODERATOR));
            userService.addRole(new Role(null, ROLE_ADMIN));

            userService.addUser(new User("admin", "admin@bmacharia.com", "admin@123*", "Super", "Admin"));
            userService.addUser(new User("visitor", "visitor@bmacharia.com", "visitor@123*", "Visitor", "User"));

            userService.addRoleToUser("admin", ROLE_ADMIN);
            userService.addRoleToUser("visitor", ROLE_VISITOR);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
