package com.yang.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.security.dao.PermMapper;
import com.yang.security.entity.Perm;
import com.yang.security.service.PermService;
import org.springframework.stereotype.Service;

/**
 * PERM (Perm)表服务实现类
 *
 * @author makejava
 * @since 2021-07-07 19:29:46
 */
@Service("permService")
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements PermService {
}