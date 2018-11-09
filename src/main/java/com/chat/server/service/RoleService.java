package com.chat.server.service;

import com.chat.server.model.Role;
import com.chat.server.model.RoleName;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void saveRole(Role role);

    void saveRoles(List<Role> roles);

    Role findRoleByRoleName(RoleName roleName);
}
