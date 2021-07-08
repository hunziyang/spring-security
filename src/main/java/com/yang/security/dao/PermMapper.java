package com.yang.security.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.security.entity.Perm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * PERM (Perm)表数据库访问层
 *
 * @author makejava
 * @since 2021-07-07 19:29:45
 */
@Repository
public interface PermMapper extends BaseMapper<Perm> {
    Set<Perm> selectPermByPhone(@Param("phone") String phone);
}