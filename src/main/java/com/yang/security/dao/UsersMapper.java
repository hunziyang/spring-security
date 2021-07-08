package com.yang.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.security.entity.Users;
import org.springframework.stereotype.Repository;

/**
 * (Users)表数据库访问层
 *
 * @author makejava
 * @since 2021-07-07 19:29:49
 */
@Repository
public interface UsersMapper extends BaseMapper<Users> {

}