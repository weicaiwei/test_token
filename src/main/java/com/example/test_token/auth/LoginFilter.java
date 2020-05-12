package com.example.test_token.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.test_token.util.JsonRequestUtil;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截登录请求的过滤器
 *
 * @auther caiwei
 * @date 2020-01-12
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthRequest(request);
        //父类的方法
        this.setDetails(request, usernamePasswordAuthenticationToken);

        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }


    //自动处理前端json或者表单提交来的登录数据
    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {

        UsernamePasswordAuthenticationToken authRequest;
        //用户密码通过json字符串传输
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){

            try {
                JSONObject usernameAndPassword = JSON.parseObject(JsonRequestUtil.getRequestString(request));
                authRequest = new UsernamePasswordAuthenticationToken(usernameAndPassword.get("username"), usernameAndPassword.get("password"));

            } catch (IOException e) {
                authRequest = new UsernamePasswordAuthenticationToken("", "");
                e.printStackTrace();
            }

        } else { //用户密码通过表单传输
            //用super的方法会导致login回调没有返回值，目前不知道原因,所以直接把父类的方法拷过来
            /*return super.attemptAuthentication(request, response);*/
            String username = obtainUsername(request);
            String password = obtainPassword(request);
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        }
        return authRequest;
    }
}
