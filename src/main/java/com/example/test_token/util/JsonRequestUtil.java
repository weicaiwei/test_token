package com.example.test_token.util;



import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 在filter中无法通过注解直接获取post请求中的json数据，该辅助类可以获得
 *
 * @auther caiwei
 * @date 2020-01-12
 */
public class JsonRequestUtil {

    private static final String HTTP_METHOD_GET ="GET";
    private static final String HTTP_METHOD_POST ="POST";

    /**
     *
     * @param request 请求体
     * @return 请求转换后的字符串
     * @throws IOException io异常
     */
    public static String getRequestString(HttpServletRequest request)
            throws IOException {
        String submitMethod = request.getMethod();
        // GET
        if (submitMethod.equalsIgnoreCase(HTTP_METHOD_GET)) {
            return new String(request.getQueryString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).replaceAll("%22", "\"");
            // POST
        } else if (submitMethod.equalsIgnoreCase(HTTP_METHOD_POST)){
            return getRequestPostString(request);
        }
        return null;
    }
    /**
     * 获取 post 请求内容
     *
     * @param request 请求体
     * @return 请求转换后的json字符串
     * @throws IOException io异常
     */
    private static String getRequestPostString(HttpServletRequest request)
            throws IOException {
        byte[] buffer = getRequestPostBytes(request);
        if (buffer == null || buffer.length == 0) {
            throw new IOException();
        }
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = StandardCharsets.UTF_8.name();
        }
        return new String(buffer, charEncoding);
    }

    /**
     *
     * @param request 请求体
     * @return 请求转换后的字节数组
     * @throws IOException io异常
     */
    private static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readLen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readLen == -1) {
                break;
            }
            i += readLen;
        }
        return buffer;
    }
}
