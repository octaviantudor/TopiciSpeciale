package com.unibuc.gatewayservice.repository;

import com.unibuc.gatewayservice.domain.security.Role;
import com.unibuc.gatewayservice.domain.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);

    @Query("SELECT r FROM Role r where r.name = :roleName")
    Optional<Role> findByNameOptional(@Param("roleName") RoleName roleName);
}
