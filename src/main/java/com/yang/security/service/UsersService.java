package com.yang.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.security.entity.Users;
import com.yang.security.vo.users.LoginRegisterVo;

/**
 * (Users)表服务接口
 *
 * @author makejava
 * @since 2021-07-07 19:29:49
 */
public interface UsersService extends IService<Users> {
    /**
     * 注册用户
     * @param loginRegisterVo
     * @return
     */
    boolean loginRegister(LoginRegisterVo loginRegisterVo);
}