package com.example.test_token.auth;

import com.alibaba.fastjson.JSON;
import com.example.test_token.util.ResultUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
@Component
public class HandlerLogout implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(JSON.toJSONString(ResultUtil.success()));
        writer.flush();
        writer.close();
    }
}
