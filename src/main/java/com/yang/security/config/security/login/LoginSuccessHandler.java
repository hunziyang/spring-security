package com.yang.security.config.security.login;

import com.alibaba.fastjson.JSON;
import com.yang.security.config.security.JwtUtils;
import com.yang.security.utils.Result;
import com.yang.security.utils.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 成功将生成的token放到Header里面
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = JwtUtils.TOKEN_PREFIX + JwtUtils.sign(principal.getUsername());
        response.setHeader(JwtUtils.AUTH_HEADER, token);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(Result.success(authentication.getAuthorities())));
    }
}
