package ait.cohort63.online_shop.security.service;

import ait.cohort63.online_shop.model.entity.Role;
import ait.cohort63.online_shop.model.entity.User;
import ait.cohort63.online_shop.repository.RoleRepository;
import ait.cohort63.online_shop.security.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private RoleRepository roleRepository;
    private SecretKey accessKey;
    private SecretKey refreshKey;

    public TokenService(RoleRepository roleRepository,
                        @Value("${key.access}") String accessSecretPhrase,
                        @Value("${key.refresh}") String refreshSecretPhrase) {
        this.roleRepository = roleRepository;
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretPhrase));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretPhrase));
    }

    public String generateAccessToken(User user) {

        // Задаем время окончания действия токена
        Instant now = Instant.now();
        Instant expiration = now.plus(7, ChronoUnit.DAYS);

        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getAuthorities())
                .claim("name", user.getUsername())
                .compact();
    }

    public String generationRefreshKey(User user) {

        Instant expiration = Instant.now().plus(30, ChronoUnit.DAYS);
        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();

    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    private boolean validateToken(String token, SecretKey secretKey) {

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaimsFromToken(String token, SecretKey secretKey) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims getAccessClaims(String accessToken) {
        return getClaimsFromToken(accessToken, accessKey);
    }

    public Claims getRefreshClaims(String refreshToken) {
        return getClaimsFromToken(refreshToken, refreshKey);
    }

    public AuthInfo mapClaimsToAuthInfo(Claims claims) {
        // необходимо извлечь username из Claims
        // необходимо извлечь roles из Claims
        String username = claims.getSubject();

        @SuppressWarnings("unchecked")
        List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("roles", List.class);

        Set<Role> authority = roles.stream()
                .map(m -> m.get("authority"))
                .map(str -> roleRepository.findByTitle(str).orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());

        return new AuthInfo(username, authority);
    }
}
