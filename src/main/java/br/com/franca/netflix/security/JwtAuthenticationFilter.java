package br.com.franca.netflix.security;

import br.com.franca.netflix.domain.exception.TokenExpiradoException;
import br.com.franca.netflix.domain.exception.TokenInvalidoException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                if (jwtTokenProvider.validarToken(token)) {
                    String username = jwtTokenProvider.getUsernameToken(token);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, null);

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (TokenExpiradoException ex) {
                logger.warn("Token expirado: {}", ex.getMessage());
                escreverErro(response, HttpStatus.UNAUTHORIZED, "Token expirado", ex.getMessage());
                return;

            } catch (TokenInvalidoException ex) {
                logger.warn("Token inválido: {}", ex.getMessage());
                escreverErro(response, HttpStatus.FORBIDDEN, "Token inválido", ex.getMessage());
                return;

            } catch (Exception ex) {
                logger.error("Erro inesperado ao validar token: {}", ex.getMessage(), ex);
                escreverErro(response, HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", "Falha na autenticação do token.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void escreverErro(HttpServletResponse response, HttpStatus status, String erro, String mensagem) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = String.format("""
                {
                    "erro": "%s",
                    "mensagem": "%s",
                    "status": %d,
                    "timestamp": "%s"
                }
                """, erro, mensagem, status.value(), new Date());

        response.getWriter().write(json);
    }
}