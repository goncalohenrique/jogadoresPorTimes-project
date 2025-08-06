package mapper;

import dto.JogadorResponseDTO;
import dto.TimeResponseDTO;
import model.Times;
import java.util.List;
import java.util.stream.Collectors;

public class TimeMapper {

    private final JogadorMapper jogadorMapper;

    public TimeMapper(JogadorMapper jogadorMapper) {
        this.jogadorMapper = jogadorMapper;
    }

    public TimeResponseDTO timeToDto(Times time)
    {
        if (time == null) return null;

        TimeResponseDTO timedto = new TimeResponseDTO();
        timedto.setIdtime(time.getIdtime());
        timedto.setNome(time.getNome());
        timedto.setFolhasal(time.getFolhaSal());
        timedto.setNumjogadores(time.getNumJogadores());

        List<JogadorResponseDTO> jogadoresdto = time.getJogadoresid()
                .stream()
                .map(jogadorMapper::jogadorToDto)
                .collect(Collectors.toList());

        timedto.setJogadores_list(jogadoresdto);
        return timedto;
    }

}
