package com.yang.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ROLE (Role)实体类
 *
 * @author makejava
 * @since 2021-07-07 19:29:47
 */
@Data
@TableName("role")
public class Role {
    /**
     * ID
     */
    private Long id;
    /**
     * ROLE_NAME
     */
    private String roleName;
    /**
     * ROLE_KEY
     */
    private String roleKey;
    /**
     * DESCRIPTION
     */
    private String description;
    /**
     * 乐观锁
     */
    @Version
    private Integer revision;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME", fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    @TableField(value = "UPDATED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

}