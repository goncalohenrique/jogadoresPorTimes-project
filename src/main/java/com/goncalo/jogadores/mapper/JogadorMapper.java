package com.goncalo.jogadores.mapper;

import com.goncalo.jogadores.dto.JogadorResponseDTO;
import com.goncalo.jogadores.model.Jogador;

import java.util.List;
import java.util.stream.Collectors;

public class JogadorMapper {

    public JogadorResponseDTO jogador_para_dto(Jogador jogador) {
        JogadorResponseDTO dto = new JogadorResponseDTO();
        dto.setIdjogador(jogador.getIdJogador());
        dto.setNome(jogador.getNome());
        dto.setSalario(jogador.getSalario());
        dto.setGols(jogador.getGols());
        if (jogador.getTime()!=null)
        {
            dto.setIdtime(jogador.getTime().getIdtime());
        }
        dto.setTime(jogador.getTime());
        return dto;
    }

    public List<JogadorResponseDTO> listaJogadores_para_dto(List<Jogador> lista_jogadores)
    {
        return lista_jogadores.stream()
                .map(this::jogador_para_dto)
                .collect(Collectors.toList());
    }

}
