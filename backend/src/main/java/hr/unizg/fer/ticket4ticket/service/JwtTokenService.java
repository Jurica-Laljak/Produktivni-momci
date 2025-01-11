package hr.unizg.fer.ticket4ticket.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.List;

public interface JwtTokenService {

    String createToken(String googleId, List<String> roles);

    String resolveToken(HttpServletRequest req);

    boolean validateToken(String token);

    String getUsernameFromToken(String token);

    Authentication getAuthenticationFromToken(String token);

    SecretKey getSecretKey();
}
