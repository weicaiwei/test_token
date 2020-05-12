package com.example.test_token.controller;


import com.example.test_token.mapper.UserMapper;
import com.example.test_token.model.UserInfo;
import com.example.test_token.util.DateUtil;
import com.example.test_token.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
@RestController
@RequestMapping(value = "info",method = RequestMethod.POST)
public class InfoController {

    @Autowired
    private UserMapper userMapper;


    @GetMapping("test")
    public Map<String, Object> test() {

        Map<String, Object> response = new HashMap<>();
        response.put("test", "info");
        return ResultUtil.success(response);
    }

}
