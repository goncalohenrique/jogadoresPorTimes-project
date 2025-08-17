    package com.goncalo.jogadores.model;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import com.goncalo.jogadores.dto.TimeRequestDTO;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.Setter;

    import java.util.ArrayList;
    import java.util.List;

    @Getter
    @Setter
    @AllArgsConstructor
    @Entity
    @Table(name = "times")
    public class Times {

        @Id
        @Column(nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idtime;
        @NotBlank
        private String nome;
        private Long folhaSal;
        private int numJogadores;
        @OneToMany(mappedBy = "time", cascade = CascadeType.ALL, orphanRemoval= true)
        @JsonManagedReference
        private List<Jogador> jogadoresid = new ArrayList<>() ;

        public Times() {
            this.folhaSal = 0L;
            this.numJogadores = 0;
            this.jogadoresid = new ArrayList<>();
        }

        public void atualizarFolhaSalarial() {
            if (jogadoresid==null)
            {
                    throw  new IllegalArgumentException("Jogador não pode ser nulo para atualizar a folha salarial!");
            }
            Long total = 0L;
            for (Jogador jogador : jogadoresid) {
                if (jogador.getSalario()!= null)
                {
                    total += jogador.getSalario();
                }
            }
            this.folhaSal = total;
            this.numJogadores = jogadoresid.size();

            //System.out.println("Folha salárial: "+ folhaSal +", número jogadores: "+numJogadores);
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

        public Times(TimeRequestDTO time_teste_dto) {
            this.nome = time_teste_dto.getNome();

        }
    }
