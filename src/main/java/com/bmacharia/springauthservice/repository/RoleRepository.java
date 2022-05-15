package com.bmacharia.springauthservice.repository;

import com.bmacharia.springauthservice.model.ERole;
import com.bmacharia.springauthservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
