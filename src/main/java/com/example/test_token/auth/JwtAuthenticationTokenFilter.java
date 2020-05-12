package com.example.test_token.auth;



import com.example.test_token.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-12
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String URL_TOKEN = "token";
    private final static String BEARER = "Bearer ";


    @Autowired
    private JwtUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //先从url中取token
        String authToken = request.getParameter(URL_TOKEN);
        String authHeader = request.getHeader(HEADER_AUTHORIZATION);
        if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(BEARER)) {
            //如果header中存在token，则覆盖掉url中的token
            authToken = authHeader.substring(BEARER.length()); // "Bearer "之后的内容
        }
        if (!StringUtils.isEmpty(authToken)) {
            if (!TokenUtil.isExpired(authToken)) {
                String username = TokenUtil.getUserName(authToken);
                if(SecurityContextHolder.getContext().getAuthentication() == null){
                    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //设置用户登录状态为已登录状态
                    log.info("authenticated user {}, setting security context", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    log.info("authenticated user {}, already has Authentication", username);
                }
            }
        }
        chain.doFilter(request, response);
    }

}
