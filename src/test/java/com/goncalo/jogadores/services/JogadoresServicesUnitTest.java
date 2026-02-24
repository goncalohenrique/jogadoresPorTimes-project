package com.goncalo.jogadores.services;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import com.goncalo.jogadores.dto.JogadorRequestDTO;
import com.goncalo.jogadores.model.Jogador;
import com.goncalo.jogadores.model.Times;
import com.goncalo.jogadores.repository.JogadorRepository;
import com.goncalo.jogadores.repository.TimeRepository;

@ExtendWith(MockitoExtension.class)
class JogadoresServicesUnitTest {

    @Mock
    private JogadorRepository jogadorRepository;

    @InjectMocks
    private JogadoresServices jogadoresServices;

    @Mock
    private TimeRepository timeRepository;

    @Test
    @DisplayName("Deve cadastrar Jogadores com sucesso (mock)")
    void cadastrar_jogadorCaso1() {
        Times time = new Times();
        time.setIdtime(1L);
        time.setNome("Barcelona");

        when(timeRepository.findById(1L)).thenReturn(Optional.of(time));

        JogadorRequestDTO jogador_teste_mock = new JogadorRequestDTO(900, "Messi", 20000000L, 1L);

        Jogador jogadorCadTeste = new Jogador(jogador_teste_mock);
        jogadorCadTeste.setIdJogador(1L);
        when(jogadorRepository.save(any(Jogador.class))).thenReturn(jogadorCadTeste);

        Jogador jogadorSalvo = jogadoresServices.cadastrar_jogador(jogador_teste_mock);

        assertThat(jogadorSalvo.getIdJogador()).isEqualTo(1L);
        verify(jogadorRepository, times(1)).save(any(Jogador.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar um jogador em time inexistente")
    void cadastrar_jogadorCaso2() {
        when(timeRepository.findById(99L)).thenReturn(Optional.empty());

        JogadorRequestDTO jogador_teste_mock = new JogadorRequestDTO(900, "Cristiano", 20000000L, 99L);

        assertThatThrownBy(() -> jogadoresServices.cadastrar_jogador(jogador_teste_mock))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Time não encontrado!");

        verify(jogadorRepository, never()).save(any(Jogador.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar um jogador null")
    void cadastrar_jogadorCaso3_Null() {
        assertThatThrownBy(() -> jogadoresServices.cadastrar_jogador(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Jogador não pode ser nulo");

        verify(jogadorRepository, never()).save(any(Jogador.class));
    }

    @Test
    void deletar_jogador_porId() {
    }
}
