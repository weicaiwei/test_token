package com.example.test_token.controller;


import com.example.test_token.mapper.UserMapper;
import com.example.test_token.model.UserInfo;
import com.example.test_token.util.DateUtil;
import com.example.test_token.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
@RestController
@RequestMapping(value = "user",method = RequestMethod.POST)
public class UserController {

    @Autowired
    private UserMapper userMapper;


    @GetMapping("test")
    public Map<String, Object> test() {

        Map<String, Object> response = new HashMap<>();
        response.put("test", "user");
        return ResultUtil.success(response);
    }

    @PostMapping("register")
    public Map<String, Object> register(@RequestBody Map<String, String> request) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(request.get("username"));
        String hashPassword = new BCryptPasswordEncoder().encode(request.get("password"));
        userInfo.setPassword(hashPassword);
        userInfo.setPhone("phone");
        userInfo.setRegisterTime(DateUtil.now());
        userMapper.addUserInfo(userInfo);
        return ResultUtil.success();
    }

}
