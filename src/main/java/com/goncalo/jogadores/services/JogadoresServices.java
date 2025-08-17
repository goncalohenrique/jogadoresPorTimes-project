package com.goncalo.jogadores.services;

import com.goncalo.jogadores.dto.JogadorRequestDTO;
import com.goncalo.jogadores.dto.JogadorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import com.goncalo.jogadores.mapper.JogadorMapper;
import com.goncalo.jogadores.model.Jogador;
import com.goncalo.jogadores.model.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.goncalo.jogadores.repository.JogadorRepository;
import com.goncalo.jogadores.repository.TimeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JogadoresServices {

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
        if (jogdto == null)
        {
            throw new IllegalArgumentException("Jogador não pode ser nulo");
        }

        // Só cadastra o jogador, se o time(id do time) existir e estiver cadastrado no BD
        Times time = timeRepository.findById(jogdto.getIdTime())
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado!"));

            Jogador jogador_cadastrado = new Jogador();
            jogador_cadastrado.setNome(jogdto.getNome());
            jogador_cadastrado.setSalario(jogdto.getSalario());
            jogador_cadastrado.setGols(jogdto.getGols());
            jogador_cadastrado.setTime(time);



            // Salva o jogador - aqui o JPA registra a associação time-jogador
            Jogador jogadorSalvo = jogadorRepository.save(jogador_cadastrado);

            // atualiza a lista de jogadores do time em memória
            // atualiza os campos folha salarial e o número de jogadores
            time.adicionarJogador(jogadorSalvo);

            // Salva o time para persistir os novos valores
            timeRepository.save(time);

            return jogadorSalvo;

    }

    public List<JogadorResponseDTO> buscar_todos_jogadores()
    {
        List<Jogador> jogadores = jogadorRepository.findAll();
        return  jogadores.stream()
                .map(jogadorMapper:: jogador_para_dto)
                .collect(Collectors.toList());
    }

    public Optional<JogadorResponseDTO> buscar_jogad_porId(Long idjog)
    {
        Optional<Jogador> jogador = jogadorRepository.findById(idjog);
        return jogador.map(jogadorMapper::jogador_para_dto);
    }

    public Jogador atualizar_jogador_porId(Long idjog, JogadorRequestDTO dto_atualizar) {
        // Verifica se o id existe
        Jogador jogador_existente = jogadorRepository.findById(idjog)
                .orElseThrow(() -> new EntityNotFoundException("Jogador não encontrado!"));

        Times time_antigo = jogador_existente.getTime();

        if (!time_antigo.getIdtime().equals(dto_atualizar.getIdTime())) {
            // Remove o jogador da lista do time antigo
            time_antigo.removerJogador(jogador_existente);
            timeRepository.save(time_antigo);

            // Busca o novo time e adiciona o jogador na lista dele
            Times novo_time = timeRepository.findById(dto_atualizar.getIdTime())
                    .orElseThrow(() -> new EntityNotFoundException("Novo time não enontrado!"));
            novo_time.adicionarJogador(jogador_existente);
            timeRepository.save(novo_time);

            // Atualiza o time do jogador
            jogador_existente.setTime(novo_time);
        } else {
            // Se o time não mudou, só atualiza a folha salarial se o salário mudou
            if (!jogador_existente.getSalario().equals(dto_atualizar.getSalario())) {
                time_antigo.atualizarFolhaSalarial();
                timeRepository.save(time_antigo);
            }
        }

        // Atualiza os demais dados do jogador
        jogador_existente.setGols(dto_atualizar.getGols());
        jogador_existente.setSalario(dto_atualizar.getSalario());
        jogador_existente.setNome(dto_atualizar.getNome());

        return jogadorRepository.save(jogador_existente);
    }

    public void deletar_jogador_porId(Long id_jogador)
    {
        Jogador jogador = jogadorRepository.findById(id_jogador)
                        .orElseThrow(()-> new EntityNotFoundException("Jogador não encontrado!"));
        jogadorRepository.delete(jogador);
    }

}
