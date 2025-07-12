package br.com.franca.netflix.infrastructure.controller;

import br.com.franca.netflix.domain.exception.RecursoDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // E-mail já cadastrado
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Object> handleRecursoDuplicado(RecursoDuplicadoException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("erro", "Conflito de dados");
        body.put("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // Validações do Bean Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidacoes(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erro -> {
            erros.put(erro.getField(), erro.getDefaultMessage());
        });

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("erro", "Campos inválidos");
        body.put("mensagens", erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 3. NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointer(NullPointerException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("erro", "Erro interno");
        body.put("mensagem", "Ocorreu um erro inesperado (NullPointerException)");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // 4. Problemas no banco de dados (DataAccessException do Spring)
    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public ResponseEntity<Object> handleDatabaseError(org.springframework.dao.DataAccessException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("erro", "Falha de banco de dados");
        body.put("mensagem", "Erro ao acessar o banco de dados. Tente novamente mais tarde.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // Erros genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleErroGeral(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("erro", "Erro interno");
        body.put("mensagem", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
