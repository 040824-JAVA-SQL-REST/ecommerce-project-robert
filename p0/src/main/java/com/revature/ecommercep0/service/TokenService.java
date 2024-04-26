package com.revature.ecommercep0.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import com.revature.ecommercep0.dto.response.Principal;
import com.revature.ecommercep0.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SuppressWarnings("deprecation")
public class TokenService {
    private String secretKey;

    public TokenService() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        props.load(is);
        secretKey = props.getProperty("secret");

    }

    public String generateToken(Principal principal) {
        return Jwts.builder()
                .setId(principal.getId())
                .setIssuer("ecommercep0")
                .setSubject(principal.getEmail())
                .claim("firstname", principal.getFirstName())
                .claim("lastname", principal.getLastName())
                .claim("roleid", principal.getRole().getRole_id())
                .claim("name", principal.getRole().getName())
                .setExpiration(new Date(System.currentTimeMillis() * 360000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Principal parseToken(String token) {
        Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
        return new Principal(
            claims.getId(),
            claims.getSubject(),
            claims.get("firstname", String.class),
            claims.get("lastname", String.class),
            new Role(
                claims.get("roleid", String.class),
                claims.get("name", String.class)
            )
        );
    }


}
