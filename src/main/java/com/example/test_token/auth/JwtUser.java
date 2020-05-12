package com.example.test_token.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-12
 */
public class JwtUser implements UserDetails {


    private String username;
    private String password;
    private String rolesJsonString;
    private List<GrantedAuthority> grantedAuthorityList;


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }



    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGrantedAuthorityList(List<GrantedAuthority> grantedAuthorityList) {
        this.grantedAuthorityList = grantedAuthorityList;
    }

    public String getRolesJsonString() {
        return rolesJsonString;
    }

    public void setRolesJsonString(String rolesJsonString) {
        this.rolesJsonString = rolesJsonString;
    }
}
