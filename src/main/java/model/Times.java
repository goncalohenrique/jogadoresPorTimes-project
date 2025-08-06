package model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "times")
public class Times {

    @Id
    private Long idtime;
    private String nome;


    @Column(name = "folhaSalarial")
    private Long folhaSal;
    @Column(name = "numJogadores")
    private int numJogadores;
    @OneToMany(mappedBy = "times", cascade = CascadeType.ALL, orphanRemoval= true)
    @JsonManagedReference
    private List<Jogador> jogadoresid = new ArrayList<>() ;


    public void atualizarFolhaSalarial() {
        Long total = 0L;
        for (Jogador jogador : jogadoresid) {
            if (jogador.getSalario()!= null)
            {
                total += jogador.getSalario();
            }
        }
        this.folhaSal =total;
        this.numJogadores = jogadoresid.size();

        System.out.println("Folha salárial: "+ folhaSal +", número jogadores: "+numJogadores);
        //printando dados para observação mais facil
    }
}
