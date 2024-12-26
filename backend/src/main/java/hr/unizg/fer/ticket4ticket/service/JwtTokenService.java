package hr.unizg.fer.ticket4ticket.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;

public interface JwtTokenService {

    public String createTokenFromUsername(String username);

    public String resolveToken(HttpServletRequest req);

    public boolean validateToken(String token);

    public String getUsernameFromToken(String token);

    public Authentication getAuthenticationFromToken(String token);

    public SecretKey getSecretKey();
}
