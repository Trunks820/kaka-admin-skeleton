package com.kakarot.skeleton.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "kaka-admin-skeleton-secret-key-2026-abcdefghijklmnopqrstuvwxyz";

    /**
     * 一小时过期
     */
    private static final long EXPIRE_TIME = 1000L * 60 * 60 * 1;

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成token
     * @param userId
     * @param username
     * @return
     */
    public static String generateToken(Long userId, String username){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_TIME);
        return Jwts.builder()
                .subject(username)          // 标准字段，放入用户名
                .claim("userId", userId) // 自定义字段
                .issuedAt(now)              // 签发时间
                .expiration(expireDate)     // 过期时间
                .signWith(KEY)              // 用密钥签名
                .compact();
    }


    /**
     * 解析token，直接返回 Claims
     * @param token
     * @return
     */
    public static Claims parseToken(String token){
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    public static Boolean isTokenExpired(String token){
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        }catch (Exception e){
            return true;
        }
    }


    /**
     * 从token拿到userId
     * @param token
     * @return
     */
    public static Long getUserId(String token){
        Claims claims = parseToken(token);
        Object userId = claims.get("userId");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }

        if(userId instanceof Long){
            return (Long) userId;
        }

        return Long.valueOf(String.valueOf(userId));
    }

    /**
     * 从token里面那username
     * @param token
     * @return
     */
    public static String getUserName(String token){
        Claims claims = parseToken(token);
        return claims.getSubject();
    }


}
