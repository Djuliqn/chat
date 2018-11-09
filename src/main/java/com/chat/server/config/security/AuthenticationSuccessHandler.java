package com.chat.server.config.security;

import com.chat.server.model.User;
import com.chat.server.model.UserTokenState;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private Long EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    private JwtTokenUtil jwtTokenUtil;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil, ObjectMapper objectMapper) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        clearAuthenticationAttributes(request);
        User user = (User) authentication.getPrincipal();

        String jws = jwtTokenUtil.generateToken(user.getUsername(), user.getRole().getAuthority());

        // Create token auth Cookie
        Cookie authCookie = new Cookie(TOKEN_COOKIE, (jws));

        authCookie.setHttpOnly(true);

        authCookie.setMaxAge(EXPIRES_IN.intValue());

        authCookie.setPath("/");
        // Add cookie to response
        response.addCookie(authCookie);

        // JWT is also in the response
        UserTokenState userTokenState = UserTokenState.builder().access_token(jws).expires_in(EXPIRES_IN).build();
        String jwtResponse = objectMapper.writeValueAsString(userTokenState);
        response.setContentType("application/json");
        response.getWriter().write(jwtResponse);

    }
}
