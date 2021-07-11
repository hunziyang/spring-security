package com.yang.security.vo.users;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class LoginSelectVo {
    // 一页有多少个
    private Integer pageSize;
    // 第几页
    private Integer pageNum;
    private String phone;
    private String username;

    public static void main(String[] args) {
        LoginSelectVo loginSelectVo = new LoginSelectVo();
        loginSelectVo.setPageNum(1);
        loginSelectVo.setPageSize(1);
        System.out.println(JSONObject.toJSONString(loginSelectVo));
    }
}
