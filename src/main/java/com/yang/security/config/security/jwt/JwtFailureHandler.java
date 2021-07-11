package com.yang.security.config.security.jwt;

import com.alibaba.fastjson.JSON;
import com.yang.security.utils.Result;
import com.yang.security.utils.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(JSON.toJSONString(Result.error(ResultCode.LOGIN_CREDENTIAL_EXISTED)));
    }
}
