package com.example.demo.security;


import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;



@Slf4j
@Service
public class TokenProvider {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    private Key getSigninKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));

    /*
		{ // header
		  "alg":"HS512"
		}.
		{ // payload
		  "sub":"40288093784915d201784916a40c0001",
		  "iss": "demo app",
		  "iat":1595733657,
		  "exp":1596597657
		}.
		// SECRET_KEY를 이용해 서명한 부분
		Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQsbNrXdJJNWkRv50_l7bPLQPwhMobT4vBOG6Q3JYjhDrKFlBSaUxZOg
     */
        // JWT Token 생성
        System.out.println(getSigninKey());
        return Jwts.builder()
                // Header에 들어갈 내용 및 서명을 위한 Secret_key
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                // payload 들어갈 내용
                .setSubject(userEntity.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();

    }

    public String validateAndGetUserId(String token) {
        // parseClaimsJws메서드가 Base 64로 디코딩 및 파싱.
        // 즉, 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용 해 서명 후, token의 서명 과 비교.
        // 위조되지 않았다면 페이로드(Claims) 리턴
        // 그 중 우리는 userId가 필요하므로 getBody를 부른다.
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token);

        return claims.getBody().getSubject();
    }

}
