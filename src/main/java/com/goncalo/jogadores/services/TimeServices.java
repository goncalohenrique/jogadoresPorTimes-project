package com.goncalo.jogadores.services;

import com.goncalo.jogadores.dto.TimeRequestDTO;
import com.goncalo.jogadores.dto.TimeResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import com.goncalo.jogadores.mapper.TimeMapper;
import com.goncalo.jogadores.model.Times;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.goncalo.jogadores.repository.TimeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeServices {
    private static final Logger logger = LoggerFactory.getLogger(TimeServices.class);

    @Autowired
    TimeRepository timeRepository;
    TimeMapper timeMapper;

    public Times cadastrar_times(TimeRequestDTO timedto) {
        logger.info("Iniciando cadastro de novo time: {}", timedto.getNome());
        
        Times time_cadastrado = new Times();
        time_cadastrado.setNome(timedto.getNome());
        Times timeSalvo = timeRepository.save(time_cadastrado);
        
        logger.info("Time {} cadastrado com ID: {}", timedto.getNome(), timeSalvo.getIdtime());
        return timeSalvo;
    }

    public List<TimeResponseDTO> buscar_todos_times() {
        logger.debug("Buscando todos os times");
        List<Times> times = timeRepository.findAll();
        logger.info("Total de {} times encontrados", times.size());
        return times.stream()
                .map(timeMapper::time_para_dto)
                .collect(Collectors.toList());
    }

    public Optional<TimeResponseDTO> buscar_time_porId(Long idTime) {
        logger.debug("Buscando time com ID: {}", idTime);
        Optional<Times> time = timeRepository.findById(idTime);
        
        if (time.isPresent()) {
            logger.info("Time com ID {} encontrado: {}", idTime, time.get().getNome());
        } else {
            logger.warn("Time com ID {} não encontrado", idTime);
        }
        
        return time.map(timeMapper::time_para_dto);
    }

    public Times atualizar_times_porId(Long id_time, TimeRequestDTO time_atuali_dto) {
        logger.info("Atualizando time com ID: {}", id_time);
        
        Times time_existente = timeRepository.findById(id_time)
                .orElseThrow(() -> {
                    logger.error("Time com ID {} não encontrado para atualização", id_time);
                    return new EntityNotFoundException("Time não encontrado!");
                });
        
        String nomeAnterior = time_existente.getNome();
        time_existente.setNome(time_atuali_dto.getNome());
        Times timeAtualizado = timeRepository.save(time_existente);
        
        logger.info("Time ID {} renomeado de {} para {}", id_time, nomeAnterior, time_atuali_dto.getNome());
        return timeAtualizado;
    }

    public void deletar_time_porId(Long id_time) {
        logger.info("Deletando time com ID: {}", id_time);
        
        Times time = timeRepository.findById(id_time)
                .orElseThrow(() -> {
                    logger.error("Time com ID {} não encontrado para deleção", id_time);
                    return new EntityNotFoundException("Time não encontrado!");
                });
        
        int qtdJogadores = time.getJogadoresid() != null ? time.getJogadoresid().size() : 0;
        timeRepository.delete(time);
        
        logger.info("Time {} deletado com sucesso (tinha {} jogadores)", id_time, qtdJogadores);
    }
}
