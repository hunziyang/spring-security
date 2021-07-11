package com.yang.security.controller;

import com.yang.security.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result test(){
        return Result.success("test");
    }

    @PostMapping("/test2")
//    @PreAuthorize("hasPermission('login:select')")
    public Result test2(){
        return Result.success("test");
    }
}
