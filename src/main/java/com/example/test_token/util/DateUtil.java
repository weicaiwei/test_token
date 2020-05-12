package com.example.test_token.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-13
 */
public class DateUtil {

    private static final String DEAFULT_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static String now() {
        return new SimpleDateFormat(DEAFULT_FORMAT).format(new Date());
    }
}
