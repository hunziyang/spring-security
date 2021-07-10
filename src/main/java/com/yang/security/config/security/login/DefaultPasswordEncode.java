package com.yang.security.config.security.login;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 默认加密方式
 */
public class DefaultPasswordEncode implements PasswordEncoder {
    /**
     *
     * @param charSequence 用户输入的密码
     * @return
     */
    @Override
    public String encode(CharSequence charSequence) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        return digester.digestHex(charSequence.toString());
    }

    /**
     *
     * @param charSequence 用户输入的密码
     * @param s 数据库获取的
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(encode(charSequence));
    }
}
