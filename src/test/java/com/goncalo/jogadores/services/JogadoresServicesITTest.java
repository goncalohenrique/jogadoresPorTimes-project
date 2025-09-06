package com.goncalo.jogadores.services;
import com.goncalo.jogadores.dto.JogadorRequestDTO;
import com.goncalo.jogadores.model.Jogador;
import com.goncalo.jogadores.model.Times;
import com.goncalo.jogadores.repository.JogadorRepository;
import com.goncalo.jogadores.repository.TimeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class JogadoresServicesITTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JogadorRepository jogadorRepository;
    @Autowired
    private TimeRepository timeRepository;


    @Test
    @DisplayName("Deve cadastrar Jogadores com sucesso no BD")
    void cadastrar_jogadorCaso1() {
    }

    @Test
    void buscar_todos_jogadores() {
    }

    @Test
    @DisplayName("Deve encontrar o Jogador com sucesso do BD")
    void buscar_jogad_porIdCaso1() {

        Long idJogador=1L;
        JogadorRequestDTO jogaDto = new JogadorRequestDTO(700, "Gonçalo", 35000000L, 1L);
        this.criar_jogador(jogaDto);

        Optional<Jogador> resultado = this.jogadorRepository.findById(idJogador);

        //Verifica se o jogador foi encontrado(metodo isPresent do Optional)
        assertThat(resultado.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Não deve encontrar o Jogador, quando o Jogador não existir no BD")
    void buscar_jogad_porIdCaso2() {

        Long idJogador=1L;

        Optional<Jogador> resultado = this.jogadorRepository.findById(idJogador);

        //Verifica se o jogador não foi encontrado(metodo isEmpty do Optional)
        assertThat(resultado.isEmpty()).isTrue();
    }


    @Test
    void atualizar_jogador_porId() {
    }

    @Test
    void deletar_jogador_porId() {
    }

    private Jogador criar_jogador(JogadorRequestDTO joga_teste_dto)
    {
        // Para que um Jogador seja criadoo, é necessário que pelo menoos um time exista
        Times timeTestarJogador = new Times();
        timeTestarJogador.setNome("Corinthians");
        timeRepository.save(timeTestarJogador);

        Jogador jogadorTeste = new Jogador(joga_teste_dto);
        this.entityManager.persist(jogadorTeste);
        return jogadorTeste;
    }

}
