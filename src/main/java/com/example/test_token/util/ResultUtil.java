package com.example.test_token.util;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-09
 */
public class ResultUtil {

    /**接口调用成功返回码*/
    private static final String SUCCESS_CODE = "0000";
    /**接口调用失败，未知异常返回码*/
    private static final String UNKNOWN_EXCEPTION = "9999";

    /**登录验证失败*/
    public static final String LOGIN_FAILURE = "2011";
    /**用户未登录或token已过期*/
    public static final String LOGIN_NON = "2012";
    /**用户无访问该接口的权限*/
    public static final String ACCESS_FAILURE = "2013";


    /**文件未找到*/
    public static final String FILE_NOT_FOUND = "1001";

    /**文件读取异常*/
    public static final String FILE_READ_ERROR = "1002";

    /**返回码与返回信息映射*/
    private static final Map<String, String> responseMap = new HashMap<>();
    static {
        responseMap.put(SUCCESS_CODE, "successful");
        responseMap.put(UNKNOWN_EXCEPTION, "服务器异常，请联系管理员");
        responseMap.put(FILE_NOT_FOUND, "文件未找到");
        responseMap.put(FILE_READ_ERROR, "文件读取异常");
        responseMap.put(LOGIN_FAILURE, "用户名不存在，或者密码错误");
        responseMap.put(LOGIN_NON, "用户未登录或token已过期");
        responseMap.put(ACCESS_FAILURE, "用户无访问该接口的权限");


    }


    /**
     * 接口正常执行完毕，无返回数据时调用
     *
     * @return 返回数据
     */
    public static Map<String, Object> success() {

        return writeResponse(SUCCESS_CODE, responseMap.get(SUCCESS_CODE),null);
    }


    /**
     * 接口正常执行完毕，有返回数据时调用
     *
     * @param data 需要返回的数据
     * @return 返回数据
     */
    public static Map<String, Object> success(Object data) {

        return writeResponse(SUCCESS_CODE, responseMap.get(SUCCESS_CODE),data);
    }

    /**
     * 接口执行时，发生可知异常时调用,异常信息已确定
     *
     * @param code 错误码
     * @return  返回数据
     */
    public static Map<String, Object> fail(String code) {

        return writeResponse(code, responseMap.get(code),null);
    }

    /**
     * 接口执行时，发生可知异常时调用,异常信息由调用处传入
     *
     * @param code 错误码
     * @return  返回数据
     */
    public static Map<String, Object> fail(String code,String message) {

        return writeResponse(code, message,null);
    }

    /**
     * 接口执行时，发生未知异常时调用
     *
     * @return 返回数据
     */
    public static Map<String, Object> fail() {

        return writeResponse(UNKNOWN_EXCEPTION, responseMap.get(UNKNOWN_EXCEPTION),null);
    }



    private static Map<String, Object> writeResponse(String code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", data);
        return response;
    }
}
