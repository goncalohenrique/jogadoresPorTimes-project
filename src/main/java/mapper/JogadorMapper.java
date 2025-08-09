package mapper;

import dto.JogadorResponseDTO;
import model.Jogador;

public class JogadorMapper {

    public JogadorResponseDTO jogadorToDto(Jogador jogador) {
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
//
}
