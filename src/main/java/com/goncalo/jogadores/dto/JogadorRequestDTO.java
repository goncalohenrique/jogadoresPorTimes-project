package com.goncalo.jogadores.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JogadorRequestDTO {

    @NotNull(message = "Informe o número de gols!")
    @Min(value = 0, message = "Número de gols não pode ser negativo!")
    private Integer gols;
    @NotBlank(message = "Nome do jogador é obrigatório!")
    private String nome;
    @NotNull(message = "Salário do jogador é obrigatório!")
    @Min(value = 0, message = "Salário não pode ser negativo!")
    private Long salario;
    @NotNull(message = "O id do time é obrigatório!")
    private Long idTime;
}
