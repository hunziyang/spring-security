package com.yang.security.controller;

import com.yang.security.service.UsersService;
import com.yang.security.utils.Result;
import com.yang.security.utils.ResultCode;
import com.yang.security.vo.users.LoginRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public Result loginRegister(@RequestBody LoginRegisterVo loginRegisterVo) {
        boolean flag = usersService.loginRegister(loginRegisterVo);
        if (!flag) {
            return Result.error(ResultCode.FAILED);
        }
        return Result.success(flag);
    }
}
