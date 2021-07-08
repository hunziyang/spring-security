package com.yang.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.security.dao.UsersMapper;
import com.yang.security.entity.Users;
import com.yang.security.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * (Users)表服务实现类
 *
 * @author makejava
 * @since 2021-07-07 19:29:49
 */
@Service("userService")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
}