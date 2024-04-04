package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.fit.travie_api.core.shared.constants.SecurityConstant;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class JwtProvider {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.application.company}")
    private String company;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    public String generateToken(UserDetails userDetails) {

        String[] claims = getClaimsFromUser(userDetails);

        long currentTime = System.currentTimeMillis();
        Date issuedAt = new Date(currentTime);
        Date expiresAt = new Date(currentTime + expirationTime);

        return JWT.create()
                  .withIssuer(company)
                  .withAudience(applicationName)
                  .withSubject(userDetails.getUsername())
                  .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
                  .withIssuedAt(issuedAt)
                  .withExpiresAt(expiresAt)
                  .sign(Algorithm.HMAC512(secret));
    }

    private JWTVerifier getJwtVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(company).build();
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private <T> T decodeJWT(
            String token,
            Function<DecodedJWT, T> resolver
    ) {
        JWTVerifier verifier = getJwtVerifier();
        return resolver.apply(verifier.verify(token));
    }

    private String[] getClaimsFromUser(UserDetails userDetails) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    public String getUsername(String token) {
        return decodeJWT(token, DecodedJWT::getSubject);
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        return !isTokenExpired(token) && username.equals(getUsername(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return decodeJWT(token, DecodedJWT::getExpiresAt);
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = decodeJWT(token, jwt -> jwt.getClaim(SecurityConstant.AUTHORITIES).asArray(String.class));
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
