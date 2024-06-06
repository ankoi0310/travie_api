package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.fit.travie_api.core.shared.constants.SecurityConstant;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${app.name}")
    private String applicationName;

    @Value("${app.company}")
    private String company;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.access-token}")
    private long accessTokenExpirationTime;

    @Value("${jwt.expiration.refresh-token}")
    private long refreshTokenExpirationTime;

    public String generateAccessToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String[] authorities = getAuthorities(userDetails);


        return getJwtBuilder(userDetails.getUsername(), accessTokenExpirationTime)
                .claim(SecurityConstant.AUTHORITIES, authorities)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return getJwtBuilder(userDetails.getUsername(), refreshTokenExpirationTime)
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private JwtBuilder getJwtBuilder(String username, long expirationTime) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationTime);

        return Jwts.builder()
                   .subject(username)
                   .issuer(String.format("%s - %s", applicationName, company))
                   .issuedAt(issuedAt)
                   .expiration(expiration)
                   .signWith(getSecretKey());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsernameFromJWT(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                   .verifyWith(getSecretKey())
                   .build().parseSignedClaims(token).getPayload();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String[] getAuthoritiesFromJWT(String token) {
        Claims claims = getClaims(token);
        return (String[]) claims.get(SecurityConstant.AUTHORITIES);
    }

    private String[] getAuthorities(UserDetails userDetails) {
        return userDetails.getAuthorities()
                          .stream()
                          .map(GrantedAuthority::getAuthority)
                          .toArray(String[]::new);
    }
}
