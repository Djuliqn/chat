package com.chat.server.service;

import com.chat.server.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findById(Long id);

    List<User> getAllUsers();

    void saveUser(User user);

    User updateUser(User user);

}
