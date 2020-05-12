package com.example.test_token.controller;


import com.example.test_token.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
@RestController
@RequestMapping(value = "open",method = RequestMethod.POST)
public class OpenController {

    @GetMapping("test")
    public Map<String, Object> test() {

        Map<String, Object> response = new HashMap<>();
        response.put("test", "open");
        return ResultUtil.success(response);
    }
}
