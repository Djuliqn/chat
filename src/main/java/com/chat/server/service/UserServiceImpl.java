package com.chat.server.service;

import com.chat.server.core.exception.ChatClientException;
import com.chat.server.model.RoleName;
import com.chat.server.model.Status;
import com.chat.server.model.User;
import com.chat.server.repository.RoleRepository;
import com.chat.server.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ChatClientException("Записът не е намерен."));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        try {
            Assert.notNull(user, "Моля попълнете данни за потребителят.");

            User newUser = User.builder()
                    .status(Status.OFFLINE)
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .role(roleRepository.findOneByRoleName(RoleName.ROLE_USER))
                    .build();

            userRepository.save(newUser);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage(), ex);
            throw new ChatClientException("Съществува запис с това име.");
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage(), ex);
            throw new ChatClientException(ex.getMessage());
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            Assert.notNull(user, "Моля попълнете данни за потребителят.");
            return userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            log.error(ex.getMessage(), ex);
            throw new ChatClientException("Съществува запис с това име.");
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage(), ex);
            throw new ChatClientException(ex.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findOneByUsername(username);

        user.orElseThrow(() -> new ChatClientException("Грешно потребителско име."));

        return user.get();
    }
}