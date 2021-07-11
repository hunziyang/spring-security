package com.yang.security.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yang.security.dao.UsersMapper;
import com.yang.security.entity.Users;
import com.yang.security.service.UsersService;
import com.yang.security.vo.users.LoginRegisterVo;
import com.yang.security.vo.users.LoginSelectVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public PageInfo<Users> usersSelect(LoginSelectVo loginSelectVo) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(loginSelectVo.getPhone())){
            wrapper.eq(Users::getPhone,loginSelectVo.getPhone());
        }
        if (!StringUtils.isEmpty(loginSelectVo.getUsername())){
            wrapper.eq(Users::getUsername,loginSelectVo.getUsername());
        }
        Page page = PageHelper.startPage(loginSelectVo.getPageSize(), loginSelectVo.getPageNum());
        List<Users> users = usersMapper.selectList(wrapper);
        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;
    }
}