package com.yang.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.yang.security.entity.Users;
import com.yang.security.vo.users.LoginRegisterVo;
import com.yang.security.vo.users.LoginSelectVo;

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

    /**
     * 查询全部的用户
     * @param loginSelectVo
     * @return
     */
    PageInfo<Users> usersSelect(LoginSelectVo loginSelectVo);
}