package com.goncalo.jogadores.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goncalo.jogadores.dto.JogadorRequestDTO;
import com.goncalo.jogadores.dto.JogadorResponseDTO;
import com.goncalo.jogadores.mapper.JogadorMapper;
import com.goncalo.jogadores.model.Jogador;
import com.goncalo.jogadores.model.Times;
import com.goncalo.jogadores.services.JogadoresServices;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JogadorController.class)
class JogadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private JogadoresServices jogadoresServices;
    private JogadorMapper jogadorMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Times time;
    private Jogador jogador;
    private JogadorRequestDTO jogadorRequestDTO;
    private JogadorResponseDTO jogadorResponseDTO;

    @Test
    @DisplayName("Deve cadastrar um novo jogador com sucesso")
    void testCadastrarJogador_Sucesso() throws Exception {
        when(jogadoresServices.cadastrar_jogador(any(JogadorRequestDTO.class))).thenReturn(jogador);
        when(jogadorMapper.jogador_para_dto(any(Jogador.class))).thenReturn(jogadorResponseDTO);

        String jsonRequest = objectMapper.writeValueAsString(jogadorRequestDTO);

        mockMvc.perform(post("/jogador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idjogador").value(1L))
                .andExpect(jsonPath("$.nome").value("Messi"))
                .andExpect(jsonPath("$.salario").value(20000000L));
    }

    @Test
    @DisplayName("Deve retornar erro de validação ao cadastrar jogador com dados inválidos")
    void testCadastrarJogador_ValidacaoInvalida() throws Exception {
        JogadorRequestDTO invalidDTO = new JogadorRequestDTO(-1, "", -1000L, 1L);
        String jsonRequest = objectMapper.writeValueAsString(invalidDTO);

        mockMvc.perform(post("/jogador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Deve listar todos os jogadores com sucesso")
    void testBuscarTodos_Sucesso() throws Exception {
        JogadorResponseDTO jogador2DTO = new JogadorResponseDTO(2L, 700, "Ronaldinho", 15000000L, 1L, time);
        List<JogadorResponseDTO> jogadores = Arrays.asList(jogadorResponseDTO, jogador2DTO);

        when(jogadoresServices.buscar_todos_jogadores()).thenReturn(jogadores);

        mockMvc.perform(get("/jogador")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idjogador").value(1L))
                .andExpect(jsonPath("$[1].idjogador").value(2L))
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));
    }

    @Test
    @DisplayName("Deve buscar um jogador específico por ID")
    void testBuscarPorId_Sucesso() throws Exception {
        when(jogadoresServices.buscar_jogad_porId(1L)).thenReturn(Optional.of(jogadorResponseDTO));

        mockMvc.perform(get("/jogador/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idjogador").value(1L))
                .andExpect(jsonPath("$.nome").value("Messi"));
    }

    @Test
    @DisplayName("Deve retornar 404 quando jogador não encontrado")
    void testBuscarPorId_NaoEncontrado() throws Exception {
        when(jogadoresServices.buscar_jogad_porId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/jogador/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar um jogador com sucesso")
    void testAtualizarJogador_Sucesso() throws Exception {
        Jogador jogadorAtualizado = new Jogador();
        jogadorAtualizado.setIdJogador(1L);
        jogadorAtualizado.setNome("Messi (atualizado)");
        jogadorAtualizado.setGols(950);
        jogadorAtualizado.setSalario(25000000L);
        jogadorAtualizado.setTime(time);

        JogadorResponseDTO dtoAtualizado = new JogadorResponseDTO(1L, 950, "Messi (atualizado)", 25000000L, 1L, time);

        when(jogadoresServices.atualizar_jogador_porId(anyLong(), any(JogadorRequestDTO.class))).thenReturn(jogadorAtualizado);
        when(jogadorMapper.jogador_para_dto(any(Jogador.class))).thenReturn(dtoAtualizado);

        JogadorRequestDTO updateDTO = new JogadorRequestDTO(950, "Messi (atualizado)", 25000000L, 1L);
        String jsonRequest = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/jogador/1/atualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gols").value(950));
    }

    @Test
    @DisplayName("Deve deletar um jogador com sucesso")
    void testDeletarJogador_Sucesso() throws Exception {
        doNothing().when(jogadoresServices).deletar_jogador_porId(1L);

        mockMvc.perform(delete("/jogador/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar erro ao deletar jogador inexistente")
    void testDeletarJogador_NaoEncontrado() throws Exception {
        doThrow(new jakarta.persistence.EntityNotFoundException("Jogador não encontrado!"))
                .when(jogadoresServices).deletar_jogador_porId(999L);

        mockMvc.perform(delete("/jogador/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
