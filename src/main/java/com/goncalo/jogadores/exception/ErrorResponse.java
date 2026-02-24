package com.goncalo.jogadores.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Resposta padronizada de erro para todas as exceções
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private LocalDateTime timestamp;

    //Código HTTP de status
    private int status;

    //Tipo de erro
    private String error;

    private String message;

    //Mapa de erros de validação (campo -> mensagem)
    private Map<String, String> validationErrors;
}
