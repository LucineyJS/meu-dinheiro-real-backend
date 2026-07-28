package com.meudinheiroreal.backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Captura erros de regras de negócio disparados pelas Services
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, String>> handleRegraNegocio(RegraNegocioException ex) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    // 2. Captura falhas do Bean Validation (@Valid, @NotBlank, @Email, @Size, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                erros.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    // 3. Fallback para erros de integridade do Banco de Dados (chaves estrangeiras, unicidade)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Não é possível realizar esta operação pois o registro está vinculado a outros dados no sistema.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    // 4. Captura qualquer outro erro inesperado no servidor (Erro 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Ocorreu um erro interno no servidor. Tente novamente mais tarde.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}
