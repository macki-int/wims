package com.mj.wims.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;



public class AuthenticationService {
    //    @Value("${jwt.expirationTime}")
    private static final long EXPIRATIONTIME = 14_400_000L;

//    @Value("${jwt.secretKey}")
    private static final String SIGNINGKEY = "asffgfg";

    static public void addJWTToken(HttpServletResponse response, String username, SimpleGrantedAuthority role ) {
        String JwtToken = Jwts.builder().setSubject(username)
                .claim("roles", role.getAuthority())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
                .compact();
        response.addHeader("Authorization", "Bearer " + JwtToken);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    static public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SIGNINGKEY)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();

            String userRole = Jwts.parser()
                    .setSigningKey(SIGNINGKEY)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .get("roles")
                    .toString();

            if (user != null) {
                //emptyList()
                return new UsernamePasswordAuthenticationToken(user, null, Arrays.asList (new SimpleGrantedAuthority(userRole)));
            } else {
                throw new RuntimeException("Authentication failed");
            }
        }
        return null;
    }

}
