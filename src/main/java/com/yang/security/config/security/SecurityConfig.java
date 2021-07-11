package com.yang.security.config.security;

import com.yang.security.config.security.jwt.JwtAuthenticationFilter;
import com.yang.security.config.security.login.DefaultPasswordEncode;
import com.yang.security.config.security.login.LoginFailureHandler;
import com.yang.security.config.security.login.LoginSuccessHandler;
import com.yang.security.config.security.login.MyUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * spring-security基础配置类
 */
@Configuration
@EnableWebSecurity
@DependsOn("userDetailsServiceImpl")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private LogoutHandler logoutHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  //CRSF禁用，因为不使用session
            .sessionManagement().disable()  //禁用session
            .formLogin().disable() //禁用form登录
            .cors()  //支持跨域
            .and()
            .authorizeRequests()
                .antMatchers("/user/register").permitAll() //运行任何人访问
                .anyRequest().authenticated()//其余路径均需认证
            .and()
            // 将拦截器添加至Security
            .addFilterBefore(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter,MyUsernamePasswordAuthenticationFilter.class)
            .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)  //logout时清除token
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()) //logout成功后返回200
            .and()
            .sessionManagement().disable();
    }


    /**
     * 用@Bean注入有问题
     * @return
     * @throws Exception
     */
    public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailureHandler());
        return filter;
    }

    @Bean("daoAuthenticationProvider")
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
        //这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return daoProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * AuthenticationManager管理AuthenticationProvider
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    /**
     * 自定义加密方式
     * @return
     */
    public PasswordEncoder passwordEncoder(){
        return new DefaultPasswordEncode();
    }


}
