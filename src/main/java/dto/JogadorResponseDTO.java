package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Times;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JogadorResponseDTO {

    private Long idjogador;
    private int gols;
    private String nome;
    private Long salario;
    private Long idtime;
    private Times time;

}
