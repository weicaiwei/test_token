package com.example.test_token.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-12
 */

@Component
@Slf4j
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //用户输入的用户名
        String username = String.valueOf(authentication.getName());
        //用户输入的密码
        String password = String.valueOf(authentication.getCredentials());

        //通过自定义的CustomUserDetailsService，以用户输入的用户名查询用户信息
        JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (Objects.isNull(userDetails)) {
            throw new BadCredentialsException("该用户不存在，请你先注册");
        }

        //用户输入密码加密后与数据库比较
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        if(!encode.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("您输入的密码错误，请重新输入");
        }
        //将用户信息塞到SecurityContext中，方便获取当前用户信息  把当前用户信息放入Security全局缓存中
        return this.createSuccessAuthentication(userDetails, authentication, userDetails);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
