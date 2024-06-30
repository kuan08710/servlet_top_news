package com.louis.top_news.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtHelper {
    private static long tokenExpiration = 24 * 60 * 60 * 1000;  // 24 小時
    private static String tokenSignKey = "123456";

    // 生成token
    public static String createToken (Long userId) {
        String token = Jwts.builder()
                           .setSubject("YYGH-USER")
                           .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                           .claim("userId" , userId)
                           .signWith(SignatureAlgorithm.HS512 , tokenSignKey)
                           .compressWith(CompressionCodecs.GZIP)
                           .compact();
        return token;
    }

    // 從 token 中獲取 userid
    public static Long getUserId (String token) {
        if (token.length() == 0 || token == null) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser()
                                    .setSigningKey(tokenSignKey)
                                    .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    // 判斷 token 是否有效
    public static boolean isExpiration (String token) {
        try {
            boolean isExpire = Jwts.parser()
                                   .setSigningKey(tokenSignKey)
                                   .parseClaimsJws(token)
                                   .getBody()
                                   .getExpiration()
                                   .before(new Date());
            return isExpire;
        }
        catch (Exception e) {
            // 過期拋異常
            return true;
        }
    }
}
