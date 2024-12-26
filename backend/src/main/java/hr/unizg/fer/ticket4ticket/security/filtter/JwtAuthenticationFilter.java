package hr.unizg.fer.ticket4ticket.security.filtter;

import hr.unizg.fer.ticket4ticket.service.impl.JwtTokenServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenServiceImpl jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenServiceImpl jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = jwtTokenService.resolveToken(request);
            if (StringUtils.hasText(jwt)) {
                if (jwtTokenService.validateToken(jwt)) {
                    Authentication authentication = jwtTokenService.getAuthenticationFromToken(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);

            this.resetAuthenticationAfterRequest();
        } catch (ExpiredJwtException eje) {
            (response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}

