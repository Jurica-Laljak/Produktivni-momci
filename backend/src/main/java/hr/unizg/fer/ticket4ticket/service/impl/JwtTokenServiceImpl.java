package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.service.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public String createToken(String googleId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        Map<String,Object> claims = new HashMap<>();
        claims.put("roles", "ROLE_USER");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(googleId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            throw new SignatureException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch(ExpiredJwtException ex){
            throw new ExpiredJwtException(ex.getHeader(), ex.getClaims(), "The Token Provided is Expired");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("JWT claims string is empty");
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public Authentication getAuthenticationFromToken(String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().replace("[", "").replace("]", "").split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    @Override
    public SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

}
