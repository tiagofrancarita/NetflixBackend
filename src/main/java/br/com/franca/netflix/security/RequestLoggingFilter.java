package br.com.franca.netflix.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.Instant;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();
        String timestamp = Instant.now().toString();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usuario = (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : "anônimo";

        logger.info("[" + timestamp + "] " + method + " " + path + " - Usuário: " + usuario);

        filterChain.doFilter(request, response);

    }
}