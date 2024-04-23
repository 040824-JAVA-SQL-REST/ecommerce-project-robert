package com.revature.ecommercep0.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Properties;

import io.jsonwebtoken.Jwts;

@SuppressWarnings("deprecation")
public class TokenService  {
    private String secretKey;

    public TokenService() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        props.load(is);
        secretKey = props.getProperty("secret");

    }
    public String generateToken(Principal principal) {
        return null; 
        // Jwts.builder()
         //   .
    }

    
}
