package com.bmacharia.springauthservice.controller;

import com.bmacharia.springauthservice.model.Article;
import com.bmacharia.springauthservice.model.ERole;
import com.bmacharia.springauthservice.model.Role;
import com.bmacharia.springauthservice.model.User;
import com.bmacharia.springauthservice.payload.*;
import com.bmacharia.springauthservice.repository.ArticleRepository;
import com.bmacharia.springauthservice.repository.RoleRepository;
import com.bmacharia.springauthservice.repository.UserRepository;
import com.bmacharia.springauthservice.service.ArticleService;
import com.bmacharia.springauthservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/res")
public class ResourceController {

    private final UserService userService;
    private final ArticleService articleService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleQuery>> getArticles() {
        return ResponseEntity.ok().body(articleService.listAllArticles());
    }

    @PostMapping("/article/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<?> saveArticle(@RequestBody ArticlePost articlePost) {
        if (articleRepository.existsByTitle(articlePost.getTitle())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Article already posted. Try another title!"));
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findByUsername(username);
        Article article = new Article(articlePost.getTitle(), articlePost.getBody(), user);
        articleRepository.save(article);
        return ResponseEntity.ok(new MessageResponse("Article posted successfully!"));
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<ArticleQuery> findArticleById(@PathVariable(value = "id") long id) {
        ArticleQuery article = articleService.getArticleById(id);

        if (article != null) {
            return ResponseEntity.ok().body(article);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        if (userRepository.existsByUsername(addUserRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(addUserRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(addUserRequest.getUsername(),
                addUserRequest.getEmail(),
                encoder.encode(addUserRequest.getPassword()), addUserRequest.getFirstname(), addUserRequest.getLastname());
        Set<String> strRoles = addUserRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    case "vis":
                        Role visRole = roleRepository.findByName(ERole.ROLE_VISITOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(visRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User added successfully!"));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.listAllUsers());
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasRole('VISITOR') or hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<User> findUserByUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info("Username for currently logged in user is: {}", username);
        //Optional<User> user = userRepository.findByUsername(username);
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/add/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUserRole(@Valid @RequestBody AddUserRoleRequest addUserRoleRequest) {
        User user = userRepository.findByUsername(addUserRoleRequest.getUsername());
        if (user != null) {
            Set<String> strRoles = addUserRoleRequest.getRole();
            Set<Role> roles = new HashSet<>();
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;
                        case "mod":
                            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                            break;
                        case "vis":
                            Role visRole = roleRepository.findByName(ERole.ROLE_VISITOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(visRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }
            user.setRoles(roles);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User role added successfully!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
