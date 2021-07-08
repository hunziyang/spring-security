package com.yang.security.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.security.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * ROLE (Role)表数据库访问层
 *
 * @author makejava
 * @since 2021-07-07 19:29:47
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    Set<Role> selectRolesByPhone(@Param("phone") String phone);
}