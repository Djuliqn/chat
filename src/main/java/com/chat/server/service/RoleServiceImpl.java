package com.chat.server.service;


import com.chat.server.core.exception.ChatClientException;
import com.chat.server.model.Role;
import com.chat.server.model.RoleName;
import com.chat.server.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void saveRole(Role role) {
        try {
            Assert.notNull(role, "Моля попълнете данни за потребителят.");

            Role newRole = Role.builder()
                    .roleName(role.getRoleName())
                    .build();

            roleRepository.save(newRole);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage(), ex);
            throw new ChatClientException("Съществуващ запис.");
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage(), ex);
            throw new ChatClientException(ex.getMessage());
        }
    }

    @Override
    public void saveRoles(List<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public Role findRoleByRoleName(RoleName roleName) {
        return roleRepository.findOneByRoleName(roleName);
    }
}
