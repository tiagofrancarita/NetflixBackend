package br.com.franca.netflix.security;

import br.com.franca.netflix.config.JwtProperties;
import br.com.franca.netflix.domain.exception.TokenExpiradoException;
import br.com.franca.netflix.domain.exception.TokenInvalidoException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final JwtProperties jwtProperties;
    private Key scretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        this.scretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    // Corrigido: recebe UserDetails por parâmetro
    public String gerarToken(UserDetails userDetails) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtProperties.getExpiration());

        var roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)  // <-- roles como lista
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(scretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(scretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Token expirado: {}", e.getMessage());
            throw new TokenExpiradoException("Token expirado. Faça login novamente.");
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Token inválido: {}", e.getMessage());
            throw new TokenInvalidoException("Token inválido.");
        }
    }

    public String getUsernameToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(scretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @SuppressWarnings("unchecked")
    public Collection<? extends GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(scretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object rolesObject = claims.get("roles");

        // Verifica se o claim é uma lista e converte para List<String>
        if (rolesObject instanceof java.util.List<?>) {
            var roles = ((java.util.List<?>) rolesObject).stream()
                    .filter(role -> role instanceof String)
                    .map(role -> (String) role)
                    .collect(Collectors.toList());

            return roles.stream()
                    .map(role -> {
                        if (role.startsWith("ROLE_")) {
                            return new SimpleGrantedAuthority(role);
                        } else {
                            return new SimpleGrantedAuthority("ROLE_" + role);
                        }
                    })
                    .collect(Collectors.toList());
        }

        // Caso não seja lista, retorna vazio
        return java.util.Collections.emptyList();
    }
}