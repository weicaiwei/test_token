package com.example.test_token.auth;

import com.alibaba.fastjson.JSON;
import com.example.test_token.util.ResultUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.Writer;


/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
@Component
public class HandlerLoginFailure implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(JSON.toJSONString(ResultUtil.fail(ResultUtil.LOGIN_FAILURE,e.getMessage())));
        writer.flush();
        writer.close();
    }
}
