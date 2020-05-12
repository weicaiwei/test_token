package com.example.test_token.model;

import lombok.Data;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
@Data
public class UserInfo {

    private String username;
    private String password;
    private String roles;
    private String phone;
    private String imageUrl;
    private String lastLoginTime;
    private String registerTime;

}
