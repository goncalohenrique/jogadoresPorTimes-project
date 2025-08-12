package com.goncalo.jogadores.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.goncalo.jogadores.dto.JogadorRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jogadores")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idJogador;
    @Column(nullable = false)
    private Integer gols;
    @NotBlank
    private String nome;
    @Column(nullable = false)
    private Long salario;

    @ManyToOne
    @JoinColumn(name = "times_idtime")
    @JsonBackReference
    private Times time;


    //Construtor para criar o EntityManager nos testes
    public Jogador(JogadorRequestDTO teste_data) {
        Times timeTeste = new Times();
        timeTeste.setIdtime(teste_data.getIdTime());
        this.time = timeTeste;
        this.gols = teste_data.getGols();
        this.nome = teste_data.getNome();
        this.salario = teste_data.getSalario();
    }
}
