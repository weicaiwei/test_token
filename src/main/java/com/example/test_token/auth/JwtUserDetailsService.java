package com.example.test_token.auth;

import com.alibaba.fastjson.JSON;
import com.example.test_token.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-12
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {

    private static final String PRE_ROLE = "ROLE_";

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUser jwtUser = userMapper.queryUserInfoByUsername(username);
        List<String> roleList = JSON.parseArray(jwtUser.getRolesJsonString(), String.class);
        if (Objects.nonNull(roleList)) {
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            for (String role : roleList) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(PRE_ROLE+role);
                grantedAuthorityList.add(grantedAuthority);
            }
            jwtUser.setGrantedAuthorityList(grantedAuthorityList);
        }

        return jwtUser;
    }
}
