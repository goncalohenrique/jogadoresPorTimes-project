package com.goncalo.jogadores.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goncalo.jogadores.dto.TimeRequestDTO;
import com.goncalo.jogadores.dto.TimeResponseDTO;
import com.goncalo.jogadores.mapper.TimeMapper;
import com.goncalo.jogadores.model.Times;
import com.goncalo.jogadores.services.TimeServices;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TimeController.class)
class TimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private TimeServices timeServices;
    private TimeMapper timeMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Times time;
    private TimeRequestDTO timeRequestDTO;
    private TimeResponseDTO timeResponseDTO;

    @Test
    @DisplayName("Deve cadastrar um novo time com sucesso")
    void testCadastrarTime_Sucesso() throws Exception {
        when(timeServices.cadastrar_times(any(TimeRequestDTO.class))).thenReturn(time);
        when(timeMapper.time_para_dto(any(Times.class))).thenReturn(timeResponseDTO);

        String jsonRequest = objectMapper.writeValueAsString(timeRequestDTO);

        mockMvc.perform(post("/times")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idtime").value(1L))
                .andExpect(jsonPath("$.nome").value("Barcelona"));
    }

    @Test
    @DisplayName("Deve retornar erro de validação ao cadastrar time sem nome")
    void testCadastrarTime_SemNome() throws Exception {
        TimeRequestDTO invalidDTO = new TimeRequestDTO("");
        String jsonRequest = objectMapper.writeValueAsString(invalidDTO);

        mockMvc.perform(post("/times")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Deve listar todos os times com sucesso")
    void testBuscarTodosTimes_Sucesso() throws Exception {
        TimeResponseDTO time2 = new TimeResponseDTO(2L, "Real Madrid", 0, 0L, new ArrayList<>());
        List<TimeResponseDTO> times = Arrays.asList(timeResponseDTO, time2);

        when(timeServices.buscar_todos_times()).thenReturn(times);

        mockMvc.perform(get("/times")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idtime").value(1L))
                .andExpect(jsonPath("$[1].idtime").value(2L))
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));
    }

    @Test
    @DisplayName("Deve buscar um time específico por ID")
    void testBuscarTimePorId_Sucesso() throws Exception {
        when(timeServices.buscar_time_porId(1L)).thenReturn(Optional.of(timeResponseDTO));

        mockMvc.perform(get("/times/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idtime").value(1L))
                .andExpect(jsonPath("$.nome").value("Barcelona"));
    }

    @Test
    @DisplayName("Deve retornar 404 quando time não encontrado")
    void testBuscarTimePorId_NaoEncontrado() throws Exception {
        when(timeServices.buscar_time_porId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/times/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar um time com sucesso")
    void testAtualizarTime_Sucesso() throws Exception {
        Times timeAtualizado = new Times();
        timeAtualizado.setIdtime(1L);
        timeAtualizado.setNome("Barcelona Updated");
        timeAtualizado.setFolhaSal(20000000L);
        timeAtualizado.setNumJogadores(1);

        TimeResponseDTO dtoAtualizado = new TimeResponseDTO(1L, "Barcelona Updated", 1, 20000000L, new ArrayList<>());

        when(timeServices.atualizar_times_porId(anyLong(), any(TimeRequestDTO.class))).thenReturn(timeAtualizado);
        when(timeMapper.time_para_dto(any(Times.class))).thenReturn(dtoAtualizado);

        TimeRequestDTO updateDTO = new TimeRequestDTO("Barcelona Updated");
        String jsonRequest = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/times/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Barcelona Updated"));
    }

    @Test
    @DisplayName("Deve deletar um time com sucesso")
    void testDeletarTime_Sucesso() throws Exception {
        doNothing().when(timeServices).deletar_time_porId(1L);

        mockMvc.perform(delete("/times/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar erro ao deletar time inexistente")
    void testDeletarTime_NaoEncontrado() throws Exception {
        doThrow(new EntityNotFoundException("Time não encontrado!"))
                .when(timeServices).deletar_time_porId(999L);

        mockMvc.perform(delete("/times/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
