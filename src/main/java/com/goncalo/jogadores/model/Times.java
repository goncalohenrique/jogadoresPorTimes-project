    package com.goncalo.jogadores.model;
    import java.util.ArrayList;
    import java.util.List;

    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import com.goncalo.jogadores.dto.TimeRequestDTO;

    import jakarta.persistence.CascadeType;
    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
            // Se a lista for nula ou vazia, define valores padrão
            if (jogadoresid == null || jogadoresid.isEmpty()) {
                this.folhaSal = 0L;
                this.numJogadores = 0;
                return;
            }
            
            Long total = 0L;
            for (Jogador jogador : jogadoresid) {
                if (jogador != null && jogador.getSalario() != null) {
                    total += jogador.getSalario();
                }
            }
            this.folhaSal = total;
            this.numJogadores = jogadoresid.size();
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
