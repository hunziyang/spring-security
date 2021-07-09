package com.yang.security.config.security.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yang.security.dao.PermMapper;
import com.yang.security.dao.RoleMapper;
import com.yang.security.dao.UsersMapper;
import com.yang.security.entity.Perm;
import com.yang.security.entity.Role;
import com.yang.security.entity.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 获取用户的相关信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private PermMapper permMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * spring-security与shiro的权限方案不同
     * GrantedAuthority将角色与权限放在在一起，区分角色需要加前缀'ROLE_'
     * 数据库用户密码默认存加密后的
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("username is empty");
        }
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getPhone, username);
        Users users = usersMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(users)) {
            throw new UsernameNotFoundException("username is null");
        }
        if (users.getIsLock() == null || users.getIsLock() == true) {
            throw new UsernameNotFoundException("username is locked");
        }
        // 权限认证
        Set<Perm> perms = permMapper.selectPermByPhone(username);
        Set<Role> roles = roleMapper.selectRolesByPhone(username);
        List<String> roleList = roles.stream().filter(role -> role != null).map(role -> "ROLE_" + role.getRoleKey()).collect(Collectors.toList());
        List<String> permList = perms.stream().filter(perm -> perm != null).map(Perm::getPermKey).collect(Collectors.toList());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if (ObjectUtils.isEmpty(roleList)) {
            roleList.stream().forEach(role -> grantedAuthorityList.add(new SimpleGrantedAuthority(role)));
        }
        if (ObjectUtils.isEmpty(permList)) {
            permList.forEach(perm -> grantedAuthorityList.add(new SimpleGrantedAuthority(perm)));
        }
        return new User(users.getUsername(), users.getPassword(), grantedAuthorityList);
    }
}
