package com.homebudget.service.authentication;

import com.homebudget.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class TokenService {
    @Autowired
    private Map<String, String> environmentVariables;

    public String generateToken() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date(System.currentTimeMillis());

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(environmentVariables.get("apiSecret"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setId("id")
                .setIssuedAt(now)
                .setSubject("auth")
                .setIssuer("budgetMaster")
                .setExpiration(new Date(now.getTime() + 2592000000L))
                .signWith(signatureAlgorithm, signingKey);

        return jwtBuilder.compact();
    }

    public boolean validateToken(String token) throws UnauthorizedException {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(environmentVariables.get("apiSecret")))
                    .parseClaimsJws(token).getBody();

        } catch (Exception e) {
            throw new UnauthorizedException();
        }

        if (claims.getId().equals(environmentVariables.get("apiId")) || claims.getId().equals(environmentVariables.get("apiIssuer")) || claims.getId().equals(environmentVariables.get("apiSubject"))) {
            return false;
        }

        return true;
    }

}
