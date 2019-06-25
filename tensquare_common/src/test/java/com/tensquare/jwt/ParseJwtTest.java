package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {
/*
*    token的解析
* */
    public static void main(String[] args) {
        Claims claims= Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_mnY4iLCJpYXQiOjE1NjExMDA1NDIsImV4cCI6MTU2MTEwMDYwMiwicm9sZSI6ImFkbWluIn0.vqEEjJE4nuZPTN3xaUIQrW89FsgyV7a3x2jnOe-42BY")
                .getBody();
        System.out.println("用户ID："+claims.getId());
        System.out.println("用户名："+claims.getSubject());
        System.out.println("用户登录时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("登录过期时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色："+claims.get("role"));

    }


}
