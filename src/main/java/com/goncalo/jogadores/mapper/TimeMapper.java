package com.goncalo.jogadores.mapper;

import com.goncalo.jogadores.dto.JogadorResponseDTO;
import com.goncalo.jogadores.dto.TimeResponseDTO;
import com.goncalo.jogadores.model.Times;
import java.util.List;
import java.util.stream.Collectors;

public class TimeMapper {

    private final JogadorMapper jogadorMapper;

    public TimeMapper(JogadorMapper jogadorMapper) {
        this.jogadorMapper = jogadorMapper;
    }

    public TimeResponseDTO time_para_dto(Times time)
    {
        if (time == null) return null;

        TimeResponseDTO timedto = new TimeResponseDTO();
        timedto.setIdtime(time.getIdtime());
        timedto.setNome(time.getNome());
        timedto.setFolhasal(time.getFolhaSal());
        timedto.setNumjogadores(time.getNumJogadores());

        List<JogadorResponseDTO> jogadoresdto = time.getJogadoresid()
                .stream()
                .map(jogadorMapper::jogador_para_dto)
                .collect(Collectors.toList());

        timedto.setJogadores_list(jogadoresdto);
        return timedto;
    }

    public List<TimeResponseDTO> listaTimes_para_dto(List<Times> lista_times)
    {
        return lista_times.stream()
                .map(this::time_para_dto)
                .collect(Collectors.toList());
    }

}
