package com.goncalo.jogadores.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRequestDTO {

    @NotBlank(message = "Nome não pode ser vazio!")
    private String nome;

    /*@NotNull(message = "O Id do time deve ser informado")//tratar o metodo de cadastro para nao permitir ids repetidos
    private Long idtime;*/
}

