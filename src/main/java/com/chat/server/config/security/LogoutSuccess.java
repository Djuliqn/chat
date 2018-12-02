package com.chat.server.config.security;

import com.chat.server.model.Status;
import com.chat.server.model.User;
import com.chat.server.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Component
public class LogoutSuccess implements LogoutSuccessHandler {

    private ObjectMapper objectMapper;
    private UserService userService;

    @Autowired
    public LogoutSuccess(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService= userService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException {
        Map<String, String> result = newHashMap();

        User user = (User) authentication.getPrincipal();
        User loadedUser = (User) userService.loadUserByUsername(user.getUsername());
        loadedUser = loadedUser.toBuilder().status(Status.OFFLINE).build();
        userService.saveUser(loadedUser);

        result.put("result", "success");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(result));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}