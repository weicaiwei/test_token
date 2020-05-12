package com.example.test_token.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private HandlerNonLogin handlerNonLogin;

    @Autowired
    private HandlerLoginSuccess handlerLoginSuccess;

    @Autowired
    private HandlerLoginFailure handlerLoginFailure;

    @Autowired
    private HandlerLogout handlerLogout;

    @Autowired
    private HandlerAccessDenied handlerAccessDenied;



    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * 配置spring security的控制逻辑
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 新加入(cors) CSRF  取消跨站请求伪造防护
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 使用 JWT，关闭session

        //用户未登录处理器
        http.httpBasic().authenticationEntryPoint(handlerNonLogin);

        http.authorizeRequests()
                // 解决跨域
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                //设置任何用户无论是否登录都可以访问的路径
                .antMatchers("/open/**", "/user/**").permitAll()
                // 任何尚未匹配的URL都只需要对用户进行身份验证  每个请求的url必须通过这个规则  RBAC 动态 url 认证
                //.anyRequest().access("@rbacAuthorityService.hasPermission(request,authentication)")
                .and()
                    .authorizeRequests()
                        .antMatchers("/admin/**").hasRole("admin")
                        .antMatchers("/dev/**").hasRole("dev")
                        .antMatchers("/info/**").hasRole("guest")
                    //其他的请求都需要用户已登录才能访问
                    .anyRequest().authenticated()
                .and()
                    //loginProcessingUrl用于指定前后端分离的时候调用后台注销接口的名称 如果启用了CSRF保护(默认)，那么请求也必须是POST
                    .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessHandler(handlerLogout);

        //无权访问处理器
        http.exceptionHandling().accessDeniedHandler(handlerAccessDenied);

        //在执行LoginFilter之前执行jwtAuthenticationTokenFilter
        http.addFilterBefore(authenticationTokenFilter, LoginFilter.class);
        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        http.headers().cacheControl();

    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(handlerLoginSuccess);
        loginFilter.setAuthenticationFailureHandler(handlerLoginFailure);
        loginFilter.setFilterProcessesUrl("/user/login");
        //重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        loginFilter.setAuthenticationManager(this.authenticationManagerBean());
        return loginFilter;
    }

}
