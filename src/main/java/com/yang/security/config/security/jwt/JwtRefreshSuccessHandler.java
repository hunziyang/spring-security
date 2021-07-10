package com.yang.security.config.security.jwt;

import com.yang.security.config.security.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 给Token刷新
 */
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = JwtUtils.TOKEN_PREFIX + JwtUtils.sign(principal.getUsername());
        response.setHeader(JwtUtils.AUTH_HEADER, token);
    }
}
