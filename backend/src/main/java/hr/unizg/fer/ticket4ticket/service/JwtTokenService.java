package hr.unizg.fer.ticket4ticket.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;

public interface JwtTokenService {

    String createToken(String username);

    String resolveToken(HttpServletRequest req);

    boolean validateToken(String token);

    String getUsernameFromToken(String token);

    Authentication getAuthenticationFromToken(String token);

    SecretKey getSecretKey();
}
