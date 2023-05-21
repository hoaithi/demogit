package com.cybersoft.demospringsecurity.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JWTHelperUtils {
    /*
     * Bước 1: tạo key
     * Bước 2: sử dụng key mới tạo để sinh ra token
     *
     *
     * */

    // keyword tìm kiếm : get data application.properties
    // @Value() giúp lấy key khai báo file application.properties
    @Value("${jwt.token.key}")
    String secretKey;


    // tạo token
    public String generateToken(String data){



        /*SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String key = Encoders.BASE64.encode(secretKey.getEncoded());
        System.out.println(key);*/




        // lấy key đã tạo trước đó sử dụng
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Dùng key để tạo ra token
        String token = Jwts.builder().setSubject(data).signWith(key).compact();
        return token;
    }


    // giải mã token
    public String validToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // chuẩn bị key để chuẩn bị giải mã
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token) // Truyền token cần giải mã
                .getBody().getSubject();
    }
}
