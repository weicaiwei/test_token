package com.example.test_token.auth;

import com.alibaba.fastjson.JSON;
import com.example.test_token.auth.JwtUser;
import com.example.test_token.util.ResultUtil;
import com.example.test_token.util.TokenUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
@Component
public class HandlerLoginSuccess implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String token = TokenUtil.generateToken(userDetails);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        Writer writer = response.getWriter();
        writer.write(JSON.toJSONString(ResultUtil.success(responseBody)));
        writer.flush();
        writer.close();
    }
}
