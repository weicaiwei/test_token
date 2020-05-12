package com.example.test_token.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT令牌由部分组成：JWT头、有效载荷、签名
 * JWT头：描述JWT元数据的JSON对象，通常如下所示：（使用Base64 URL算法将上述JSON对象转换为字符串保存）
 *     {
 *         "alg": "HS256",
 *         "typ": "JWT"
 *     }
 *
 * 有效载荷：JWT的主体内容部分，也是一个JSON对象，包含需要传递的数据（也使用Base64 URL算法转换为字符串保存）
 *     JWT指定七个默认字段供选择： iss：发行人、exp：到期时间、sub：主题、aud：用户、nbf：在此之前不可用、iat：发布时间、jti：JWT ID用于标识该JWT
 *     除以上默认字段外，我们还可以自定义私有字段，如下例：
 *         {
 *             "name": "caiwei",
 *             "roles": ["user","dev"],
 *             "exp":1494928384539
 *          }
 *
 * 签名哈希: 签名哈希部分是对上面两部分数据签名，通过指定的算法生成哈希，以确保数据不会被篡改
 *
 * 首先，需要指定一个密码（secret）。该密码仅仅为保存在服务器中，并且不能向用户公开。
 * 然后，使用标头中指定的签名算法（默认情况下为HMAC SHA256）根据以下公式生成签名。
 * HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload),secret)
 *
 * 在计算出签名哈希后，JWT头，有效载荷和签名哈希的三个部分组合成一个字符串，每个部分用"."分隔，就构成整个JWT对象。
 *
 *
 * @auther caiwei
 * @date 2020-01-12
 */
@Slf4j
@Component
public class TokenUtil {

    /**
     * 由于静态属性无法直接用@Value注入，所以这样曲线救国
     * @param secret 参数
     */
    @Value("${jwt.token.secret}")
    public void setSecret(String secret) {
        TokenUtil.secret = secret;
    }
    @Value("${jwt.token.aliveTime}")
    public void setAliveTime(Integer aliveTime) {
        TokenUtil.aliveTime = aliveTime;
    }

    //签名秘钥
    private static String secret;
    //有效时间(秒)
    private static Integer aliveTime;


    private static final String KEY_NAME = "name";
    private static final String KEY_ROLES = "roles";


    public static String getUserName(String token) {
        String username;
        try {
            username = (String)getClaims(token).get(KEY_NAME);
        } catch (Exception e) {
            username = null;
        }
        return username;

    }

    public static List<String> getUserRoles(String token) {
        List<String> roleList;
        try {
            roleList = (List<String>)getClaims(token).get(KEY_ROLES);
        } catch (Exception e) {
            roleList = null;
        }
        return roleList;
    }

    public static Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }


    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(KEY_NAME, userDetails.getUsername());
        claims.put(KEY_ROLES, userDetails.getAuthorities());
        return generateToken(claims);
    }

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + aliveTime * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    private static Claims getClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    public static Boolean isExpired(String token) {
        try {
            Date expiration = getExpirationDate(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public static String refreshToken(String token) {
        final Claims claims = getClaims(token);
        return generateToken(claims);
    }



}
