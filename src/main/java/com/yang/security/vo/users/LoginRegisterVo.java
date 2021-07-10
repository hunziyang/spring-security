package com.yang.security.vo.users;

import lombok.Data;

/**
 * 前端界面需要填的
 */
@Data
public class LoginRegisterVo {
    private String phone;
    private String password;
    private String username;
    private Boolean isLock;
    private String mail;
    private Boolean gender;
}
