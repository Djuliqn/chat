package com.chat.server.runner;

import com.chat.server.model.Role;
import com.chat.server.model.RoleName;
import com.chat.server.model.Status;
import com.chat.server.model.User;
import com.chat.server.repository.UserRepository;
import com.chat.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ChatCommandLineRunner {

    @Order(1)
    @Component
    private static class RoleCommandLineRunner implements CommandLineRunner {

        private final RoleService roleService;

        @Autowired
        public RoleCommandLineRunner(RoleService roleService) {
            this.roleService = roleService;
        }

        @Override
        public void run(String... args) {
            List<Role> roles = roleService.getAllRoles();

            if (CollectionUtils.isEmpty(roles)) {
                for (RoleName roleName : RoleName.values()) {
                    roles.add(Role.builder().roleName(roleName).build());
                }

                roleService.saveRoles(roles);
                return;
            }

            List<Role> newRoles = newArrayList();
            for (RoleName roleName : RoleName.values()) {
                boolean isExists = false;
                for (Role role : roles) {
                    if (role.getRoleName().equals(roleName)) {
                        isExists = true;
                    }
                }

                if (isExists) {
                    newRoles.add(Role.builder().roleName(roleName).build());
                }
            }

            roleService.saveRoles(newRoles);
        }
    }

    @Order(2)
    @Component
    private static class UserCommandLineRunner implements CommandLineRunner {

        private final UserRepository userRepository;
        private final RoleService roleService;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        private static final String USERNAME = "Джулиян";
        private static final String PASSWORD = "0898351444";

        @Autowired
        public UserCommandLineRunner(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
            this.userRepository = userRepository;
            this.roleService = roleService;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        @Override
        public void run(String... args) {
            if (!userRepository.findOneByUsername(USERNAME).isPresent()) {
                User user = User.builder()
                        .username(USERNAME)
                        .password(bCryptPasswordEncoder.encode(PASSWORD))
                        .status(Status.OFFLINE)
                        .role(roleService.findRoleByRoleName(RoleName.ROLE_ADMIN))
                        .build();

                userRepository.save(user);
            }
        }
    }
}
