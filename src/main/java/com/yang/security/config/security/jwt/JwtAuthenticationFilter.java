package com.yang.security.config.security.jwt;

import com.yang.security.config.redis.KeyMap;
import com.yang.security.config.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    private RequestMatcher requiresAuthenticationRequestMatcher;
    @Autowired
    @Qualifier("defaultRedisTemplate")
    private RedisTemplate redisTemplate;
    // 距离过期时间1分钟
    private static final long Before_TIME = 60 * 1000;

    public JwtAuthenticationFilter() {
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher(JwtUtils.AUTH_HEADER);
    }

    /**
     * 获取真正的Token
     *
     * @param request
     * @return
     */
    protected String getJwtToken(HttpServletRequest request) {
        String authInfo = request.getHeader(JwtUtils.AUTH_HEADER);
        return StringUtils.removeStart(authInfo, JwtUtils.TOKEN_PREFIX);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!requiresAuthentication(request)) {
            // 如果没有header直接放行,交由剩下的过滤器进行处理
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = getJwtToken(request);
            if (!StringUtils.isEmpty(token) && !JwtUtils.isTokenExpired(token)) {
                String phone = JwtUtils.getClaimFiled(token, "phone");
                if (JwtUtils.verify(token, phone)) {
                    UsernamePasswordAuthenticationToken authentication = null;
                    if (redisTemplate.hasKey(KeyMap.REDIS_AUTH) && redisTemplate.opsForHash().hasKey(KeyMap.REDIS_AUTH, phone)) {
                        List<GrantedAuthority> grantedAuthorities = (List) redisTemplate.opsForHash().get(KeyMap.REDIS_AUTH, phone);
                        UserDetails userDetails = new User(phone, "", grantedAuthorities);
                        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
                    } else {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
                        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    }
                    SecurityContextHolder.getContext().setAuthentication(authentication);  // 在上下文中记录UserDetails
                    if (isAlmostExpired(token)) {
                        new JwtRefreshSuccessHandler().onAuthenticationSuccess(request, response, authentication);
                    }
                } else {
                    new JwtFailureHandler().onAuthenticationFailure(request, response, null);
                    return;
                }
            } else {
                new JwtFailureHandler().onAuthenticationFailure(request, response, null);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JwtAuthenticationFilterErr:", e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 判断token是否即将过期，即将过期刷新token
     *
     * @param token
     * @return
     */
    private boolean isAlmostExpired(String token) {
        Date date = JwtUtils.getIssuedAt(token);
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.add(Calendar.MINUTE, (int) ((JwtUtils.EXPIRE_TIME - Before_TIME) / 1000 / 60));
        Calendar calendar = Calendar.getInstance();
        return calendar.after(time);
    }

    /**
     * 判断请求头中是否有Authorization
     *
     * @param request
     * @return
     */
    protected boolean requiresAuthentication(HttpServletRequest request) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }
}
