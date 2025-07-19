package br.com.franca.netflix.infrastructure.controller;

import br.com.franca.netflix.domain.exception.CategoriaJaCadastradaException;
import br.com.franca.netflix.domain.exception.CategoriaNãoEncontradaException;
import br.com.franca.netflix.domain.exception.EmailJaCadastradoException;
import br.com.franca.netflix.domain.exception.TokenExpiradoException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String erro, String mensagem, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("erro", erro);
        body.put("mensagem", mensagem);
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException e) {
        return buildErrorResponse("Erro de execução", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointer(NullPointerException e) {
        return buildErrorResponse("Erro interno", "Ocorreu um erro inesperado (NullPointerException)", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException e) {
        return buildErrorResponse("Violação de integridade", "Erro ao processar dados. Verifique os campos informados.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccess(DataAccessException e) {
        return buildErrorResponse("Falha de banco de dados", "Erro ao acessar o banco de dados. Tente novamente mais tarde.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidations(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                erros.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("erro", "Campos inválidos");
        body.put("mensagens", erros);
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(TokenExpiradoException.class)
    public ResponseEntity<?> handleTokenExpirado(TokenExpiradoException ex) {
        return buildErrorResponse("Token expirado", ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<?> handleEmailDuplicado(EmailJaCadastradoException ex) {
        return buildErrorResponse("E-mail já cadastrado", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaJaCadastradaException.class)
    public ResponseEntity<?> handleCategoriaJaCadastrada(CategoriaJaCadastradaException ex) {
        return buildErrorResponse("Categoria já cadastrada", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoriaNãoEncontradaException.class)
    public ResponseEntity<?> handleCategoriaNaoEncontrada(CategoriaNãoEncontradaException ex) {
        return buildErrorResponse("Categoria não encontrada", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeral(Exception e) {
        return buildErrorResponse("Erro interno", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}