package model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    @Column(nullable = false)
    private Long idtime;
    @NotBlank
    private String nome;
    @Column(name = "folhaSalarial", nullable = false)
    private Long folhaSal;
    @Column(nullable = false)
    private int numJogadores;
    @OneToMany(mappedBy = "time", cascade = CascadeType.ALL, orphanRemoval= true)
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
        this.folhaSal = total;
        this.numJogadores = jogadoresid.size();

        System.out.println("Folha salárial: "+ folhaSal +", número jogadores: "+numJogadores);
        //printando dados para observação mais facil
    }

    public void adicionarJogador(Jogador jogador) {
        jogadoresid.add(jogador);
        atualizarFolhaSalarial();
    }

    public void removerJogador(Jogador jogador) {
        jogadoresid.remove(jogador);
        atualizarFolhaSalarial();
    }

}
