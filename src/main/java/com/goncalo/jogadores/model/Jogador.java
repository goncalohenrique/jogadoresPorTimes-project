package com.goncalo.jogadores.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

}
