package com.yang.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * (Users)实体类
 *
 * @author makejava
 * @since 2021-07-07 19:29:49
 */
@Data
@TableName("users")
public class Users {
    /**
     * ID
     */
    private Long id;
    /**
     * PHONE
     */
    private String phone;
    /**
     * PASSWORD
     */
    private String password;
    /**
     * USERNAME
     */
    private String username;
    /**
     * 锁
     */
    @TableField("ISLOCK")
    private Boolean isLock;
    /**
     * MAIL
     */
    private String mail;
    /**
     * GENDER
     */
    private Boolean gender;
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