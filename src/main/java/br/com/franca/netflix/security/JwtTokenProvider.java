package br.com.franca.netflix.security;

import br.com.franca.netflix.config.JwtProperties;
import br.com.franca.netflix.domain.exception.TokenExpiradoException;
import br.com.franca.netflix.domain.exception.TokenInvalidoException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);


    private final JwtProperties jwtProperties;
    private Key scretKey;

    public JwtTokenProvider(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;

    }

    @PostConstruct
    public void init(){
        // Inicializa a chave secreta convertendo a string para key segura HMAC SHA256
        this.scretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(String username){

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(scretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validarToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(scretKey)
                    .build()
                    .parseClaimsJws(token); // parse completo com assinatura

            return true;

        } catch (ExpiredJwtException e) {
            logger.warn("Token expirado: {}", e.getMessage());
            throw new TokenExpiradoException("Token expirado. Faça login novamente.");

        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Token inválido: {}", e.getMessage());
            throw new TokenInvalidoException("Token inválido.");
        }
    }

    public String getUsernameToken(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(scretKey)
                .build()
                .parseClaimsJwt(token)
                .getBody();

        return claims.getSubject();

    }
}