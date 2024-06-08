package vn.edu.hcmuaf.fit.travie_api.core.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.travie_api.core.shared.constants.SecurityConstant;
import vn.edu.hcmuaf.fit.travie_api.service.AuthenticationService;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("request.getServletPath(): " + request.getServletPath());
        if (Arrays.stream(SecurityConstant.PUBLIC_URLS).toList().contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            System.out.println("authHeader: " + authHeader);
            String token = authHeader.substring(7);
            String username = jwtProvider.getUsernameFromJWT(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = authenticationService.loadUserByUsername(username);

                if (jwtProvider.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (ExpiredJwtException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), SecurityConstant.EXPIRED_TOKEN_MESSAGE);
            return;
        } catch (MalformedJwtException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), SecurityConstant.INVALID_TOKEN_MESSAGE);
            return;
        } catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(),
                    SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
