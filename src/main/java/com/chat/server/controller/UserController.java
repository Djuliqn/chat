package com.chat.server.controller;

import com.chat.server.adapter.UserViewAdapter;
import com.chat.server.core.ErrorBuilder;
import com.chat.server.core.exception.ChatClientException;
import com.chat.server.model.User;
import com.chat.server.service.UserService;
import com.chat.server.view.UserView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/user", consumes = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserView> register(@RequestBody @Valid UserView userView, final BindingResult result) {
        if(result.hasErrors()) {
            log.error("Errors in register method validations.");
            throw new ChatClientException(ErrorBuilder.buildErrorMessage(result));
        }

        userView = userView.toBuilder().password(bCryptPasswordEncoder.encode(userView.getPassword())).build();

        final User user = UserViewAdapter.adapt(userView);
        userService.saveUser(user);

        return ResponseEntity.ok(UserViewAdapter.adapt(user));
    }

    @GetMapping(value = "/online-users")
    public ResponseEntity<List<UserView>> getAllOnlineUsers() {
        return ResponseEntity.ok(userService.getAllOnlineUsers().stream().map(UserViewAdapter::adapt).collect(Collectors.toList()));
    }
}
