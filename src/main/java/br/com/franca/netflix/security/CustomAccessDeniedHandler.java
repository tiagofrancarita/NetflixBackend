package br.com.franca.netflix.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler  implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        logger.warn("Acesso negado para URI: {} - Motivo: {}", request.getRequestURI(), accessDeniedException.getMessage());


        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = String.format("""
                {
                    "erro": "Acesso Negado",
                    "mensagem": "Você não possui permissão para acessar este recurso.",
                    "status": 403,
                    "timestamp": "%s"
                }
                """, new Date());

        response.getWriter().write(json);
    }
}