package com.goncalo.jogadores.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goncalo.jogadores.dto.JogadorRequestDTO;
import com.goncalo.jogadores.dto.JogadorResponseDTO;
import com.goncalo.jogadores.mapper.JogadorMapper;
import com.goncalo.jogadores.model.Jogador;
import com.goncalo.jogadores.model.Times;
import com.goncalo.jogadores.repository.JogadorRepository;
import com.goncalo.jogadores.repository.TimeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class JogadoresServices {
    private static final Logger logger = LoggerFactory.getLogger(JogadoresServices.class);

    JogadorRepository jogadorRepository;
    JogadorMapper jogadorMapper;
    TimeRepository timeRepository;

    @Autowired
    public JogadoresServices(JogadorRepository jogadorRepository, TimeRepository timeRepository) {
        this.jogadorRepository = jogadorRepository;
        this.timeRepository = timeRepository;
    }

    public Jogador cadastrar_jogador(JogadorRequestDTO jogdto)
    {
        logger.info("Iniciando cadastro de novo jogador: {}", jogdto != null ? jogdto.getNome() : "Jogador nulo");
        
        if (jogdto == null) {
            logger.error("Tentativa de cadastro com DTO nulo");
            throw new IllegalArgumentException("Jogador não pode ser nulo");
        }

        // Só cadastra o jogador, se o time(id do time) existir e estiver cadastrado no BD
        Times time = timeRepository.findById(jogdto.getIdTime())
                .orElseThrow(() -> {
                    logger.error("Time com ID {} não encontrado para cadastro de jogador", jogdto.getIdTime());
                    return new EntityNotFoundException("Time não encontrado!");
                });

        Jogador jogador_cadastrado = new Jogador();
        jogador_cadastrado.setNome(jogdto.getNome());
        jogador_cadastrado.setSalario(jogdto.getSalario());
        jogador_cadastrado.setGols(jogdto.getGols());
        jogador_cadastrado.setTime(time);

        // Salva o jogador - aqui o JPA registra a associação time-jogador
        Jogador jogadorSalvo = jogadorRepository.save(jogador_cadastrado);
        logger.info("Jogador {} cadastrado com ID: {} no time: {}", 
            jogdto.getNome(), jogadorSalvo.getIdJogador(), time.getNome());

        // atualiza a lista de jogadores do time em memória
        // atualiza os campos folha salarial e o número de jogadores
        time.adicionarJogador(jogadorSalvo);

        // Salva o time para persistir os novos valores
        timeRepository.save(time);
        logger.info("Folha salarial do time {} atualizada. Nova folha: {} com {} jogadores", 
            time.getNome(), time.getFolhaSal(), time.getNumJogadores());

        return jogadorSalvo;
    }

    public List<JogadorResponseDTO> buscar_todos_jogadores() {
        logger.debug("Buscando todos os jogadores");
        List<Jogador> jogadores = jogadorRepository.findAll();
        logger.info("Total de {} jogadores encontrados", jogadores.size());
        return jogadores.stream()
                .map(jogadorMapper::jogador_para_dto)
                .collect(Collectors.toList());
    }

    public Optional<JogadorResponseDTO> buscar_jogad_porId(Long idjog) {
        logger.debug("Buscando jogador com ID: {}", idjog);
        Optional<Jogador> jogador = jogadorRepository.findById(idjog);
        if (jogador.isPresent()) {
            logger.info("Jogador com ID {} encontrado: {}", idjog, jogador.get().getNome());
        } else {
            logger.warn("Jogador com ID {} não encontrado", idjog);
        }
        return jogador.map(jogadorMapper::jogador_para_dto);
    }

    public Jogador atualizar_jogador_porId(Long idjog, JogadorRequestDTO dto_atualizar) {
        logger.info("Atualizando jogador com ID: {}", idjog);
        
        // Verifica se o id existe
        Jogador jogador_existente = jogadorRepository.findById(idjog)
                .orElseThrow(() -> {
                    logger.error("Jogador com ID {} não encontrado para atualização", idjog);
                    return new EntityNotFoundException("Jogador não encontrado!");
                });

        Times time_antigo = jogador_existente.getTime();

        if (!time_antigo.getIdtime().equals(dto_atualizar.getIdTime())) {
            logger.info("Transferindo jogador {} do time {} para time {}", 
                idjog, time_antigo.getNome(), dto_atualizar.getIdTime());
            
            // Remove o jogador da lista do time antigo
            time_antigo.removerJogador(jogador_existente);
            timeRepository.save(time_antigo);

            // Busca o novo time e adiciona o jogador na lista dele
            Times novo_time = timeRepository.findById(dto_atualizar.getIdTime())
                    .orElseThrow(() -> {
                        logger.error("Novo time com ID {} não encontrado", dto_atualizar.getIdTime());
                        return new EntityNotFoundException("Novo time não encontrado!");
                    });
            novo_time.adicionarJogador(jogador_existente);
            timeRepository.save(novo_time);

            // Atualiza o time do jogador
            jogador_existente.setTime(novo_time);
            logger.info("Jogador {} transferido para o time {}", idjog, novo_time.getNome());
        } else {
            // Se o time não mudou, só atualiza a folha salarial se o salário mudou
            if (!jogador_existente.getSalario().equals(dto_atualizar.getSalario())) {
                logger.info("Atualizando salário do jogador {} de {} para {}", 
                    idjog, jogador_existente.getSalario(), dto_atualizar.getSalario());
                time_antigo.atualizarFolhaSalarial();
                timeRepository.save(time_antigo);
            }
        }

        // Atualiza os demais dados do jogador
        jogador_existente.setGols(dto_atualizar.getGols());
        jogador_existente.setSalario(dto_atualizar.getSalario());
        jogador_existente.setNome(dto_atualizar.getNome());

        Jogador jogadorAtualizado = jogadorRepository.save(jogador_existente);
        logger.info("Jogador {} atualizado com sucesso", idjog);
        
        return jogadorAtualizado;
    }

    public void deletar_jogador_porId(Long id_jogador) {
        logger.info("Deletando jogador com ID: {}", id_jogador);
        
        Jogador jogador = jogadorRepository.findById(id_jogador)
                .orElseThrow(() -> {
                    logger.error("Jogador com ID {} não encontrado para deleção", id_jogador);
                    return new EntityNotFoundException("Jogador não encontrado!");
                });
        
        jogadorRepository.delete(jogador);
        logger.info("Jogador {} deletado com sucesso. Atualizando folha salarial do time...", id_jogador);
        
        // Atualiza folha salarial do time
        jogador.getTime().removerJogador(jogador);
        timeRepository.save(jogador.getTime());
    }
}
