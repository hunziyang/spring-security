package com.yang.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.security.dao.RoleMapper;
import com.yang.security.entity.Role;
import com.yang.security.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * ROLE (Role)表服务实现类
 *
 * @author makejava
 * @since 2021-07-07 19:29:48
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}