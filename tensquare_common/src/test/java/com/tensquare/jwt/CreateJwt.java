package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwt {
/* 创建类Create，用于生成token
 *  加密 密码令牌生成
 *  setIssuedAt用于设置签发时间
 * signWith用于设置签名秘钥
 * setExpiration 设置过期时间
 *claim 添加自定义的属性
* */
    public static void main(String[] args) {
        JwtBuilder jwtBuilder= Jwts.builder()
                .setId("666")
                .setSubject("小李")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"itcast")
                .setExpiration(new Date(new Date().getTime()+60000))
                .claim("role","admin");
        System.out.println(jwtBuilder.compact());

    }




}


