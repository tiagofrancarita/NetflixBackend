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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        logger.info("Authorization header: {}", header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                if (jwtTokenProvider.validarToken(token)) {
                    String username = jwtTokenProvider.getUsernameToken(token);
                    logger.info("Token válido para usuário: {}", username);

                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    logger.info("UserDetails username: {}, authorities: {}", userDetails.getUsername(), userDetails.getAuthorities());

                    var authoritiesFromToken = jwtTokenProvider.getAuthoritiesFromToken(token);
                    logger.info("Authorities extraídas do token: {}", authoritiesFromToken);

                    // Usar as autoridades do UserDetails (mais seguro)
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.info("Authentication set no contexto de segurança");
                }
            } catch (Exception ex) {
                logger.error("Erro na validação do token: ", ex);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            logger.info("Header Authorization ausente ou não começa com Bearer");
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