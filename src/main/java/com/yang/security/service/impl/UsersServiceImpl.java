package com.yang.security.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.security.dao.UsersMapper;
import com.yang.security.entity.Users;
import com.yang.security.service.UsersService;
import com.yang.security.vo.users.LoginRegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (Users)表服务实现类
 *
 * @author makejava
 * @since 2021-07-07 19:29:49
 */
@Service("userService")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional
    public boolean loginRegister(LoginRegisterVo loginRegisterVo) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        Users users = new Users();
        BeanUtils.copyProperties(loginRegisterVo, users);
        users.setIsLock(false);
        users.setPassword(digester.digestHex(users.getPassword()));
        int insert = usersMapper.insert(users);
        if (insert > 0) {
            return true;
        }
        return false;
    }
}