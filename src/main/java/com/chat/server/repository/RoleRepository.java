package com.chat.server.repository;

import com.chat.server.model.Role;
import com.chat.server.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findOneByRoleName(RoleName name);
}
