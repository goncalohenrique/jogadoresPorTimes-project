package com.goncalo.jogadores.services;

import com.goncalo.jogadores.dto.JogadorRequestDTO;
import com.goncalo.jogadores.model.Jogador;
import com.goncalo.jogadores.model.Times;
import com.goncalo.jogadores.repository.JogadorRepository;
import com.goncalo.jogadores.repository.TimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


//import static org.assertj.core.api.Assertions.*;
import  static  org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JogadoresServicesUnitTest {

    //Caso algum teste dependa de uma classe, Mockito cria uma versao "fake", tipo um dublê dessa classe
    //Que só faz o que é necessário para os testes ocorrerem
    @Mock
    private JogadorRepository jogadorRepository;
    //injeta o Mock jogadorRepository aqui:
    @InjectMocks
    private JogadoresServices jogadoresServices;

    @Mock
    private TimeRepository timeRepository;

    @BeforeEach
    void setup()
    {
        //Inicializando os Mocks nessa classe
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Deve cadastrar Jogadores com sucesso (mock)")
    void cadastrar_jogadorCaso1() {
        Times time = new Times();
        time.setIdtime(1L);
        time.setNome("Barcelona");

        // Mock do TimeRepository
        when(timeRepository.findById(1L)).thenReturn(Optional.of(time));

        JogadorRequestDTO jogador_teste_mock= new JogadorRequestDTO(900, "Messi", 20000000L, 1L);

        // Mock do JogadorRepository
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

        // Mock do TimeRepository(para retornar vazio)
        when(timeRepository.findById(99L)).thenReturn(Optional.empty());

        //Jogador com um time inexistente
        JogadorRequestDTO jogador_teste_mock= new JogadorRequestDTO(900, "Cristiano", 20000000L, 99L);

        assertThatThrownBy(()->jogadoresServices.cadastrar_jogador(jogador_teste_mock))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Time não encontrado!");

        //Verifica se o jogadorRepository nunca foi chamado
        verify(jogadorRepository, never()).save(any(Jogador.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar um jogador sem nome")
    void cadastrar_jogadorCaso3() {
        // Mock do TimeRepository
        Times time = new Times();
        time.setIdtime(8L);
        time.setNome("Liverpool");
        when(timeRepository.findById(8L)).thenReturn(Optional.of(time));

        //Jogador sem nome
        JogadorRequestDTO jogador_teste_mock= new JogadorRequestDTO(200, "", 200000L, 8L);

        assertThatThrownBy(()->jogadoresServices.cadastrar_jogador(jogador_teste_mock))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nome do jogador não pode ser vazio!");

        //Verifica se o jogadorRepository nunca foi chamado
        verify(jogadorRepository, never()).save(any(Jogador.class));
    }

    @Test
    void deletar_jogador_porId() {
    }

}