package com.chat.server.config.security;

import com.chat.server.model.Status;
import com.chat.server.model.User;
import com.chat.server.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    public LogoutSuccess(ObjectMapper objectMapper, UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.userRepository= userRepository;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException {
        Map<String, String> result = newHashMap();

        User user = (User) authentication.getPrincipal();
        userRepository.save(user.toBuilder().status(Status.OFFLINE).build());

        result.put("result", "success");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(result));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}