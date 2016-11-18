package com.litkowska.martyna.hairdresser.app.security.utilities;


import com.litkowska.martyna.hairdresser.app.security.models.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtToken {

    @Value("${com.litkowska.martyna.hairdresser.salt}")
    private String secret;

    public String encode(JwtUser user) {
        Claims claims = Jwts.claims().setSubject(user.getUserId());
        claims.put("role", user.getRole());
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public JwtUser decode(String token) {
        JwtUser user = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            user = new JwtUser();
            user.setUserId(body.getSubject());
            user.setRole((String) body.get("role"));
        } catch (JwtException e) {

            e.printStackTrace();
        }

        return user;
    }

}
