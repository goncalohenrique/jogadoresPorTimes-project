package com.goncalo.jogadores.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeResponseDTO {

    private long idtime;
    private String nome;
    private int numjogadores;
    private Long folhasal;
    private List<JogadorResponseDTO> jogadores_list;
}
