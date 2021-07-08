package com.yang.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * PERM (Perm)实体类
 *
 * @author makejava
 * @since 2021-07-07 19:29:44
 */
@Data
@TableName("perm")
public class Perm {
    /**
     * ID
     */
    private Long id;
    /**
     * PERM_NAME
     */
    private String permName;
    /**
     * PERM_KEY
     */
    private String permKey;
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