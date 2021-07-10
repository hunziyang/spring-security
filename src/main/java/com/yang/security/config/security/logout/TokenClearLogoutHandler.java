package com.yang.security.config.security.logout;

import com.yang.security.config.redis.KeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenClearLogoutHandler implements LogoutHandler {
    @Autowired
    @Qualifier("defaultRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        redisTemplate.opsForHash().delete(KeyMap.REDIS_AUTH, principal.getUsername());
    }
}
