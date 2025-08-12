package com.goncalo.jogadores.repository;

import com.goncalo.jogadores.dto.JogadorRequestDTO;
import com.goncalo.jogadores.model.Jogador;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class JogadorRepositoryTest {
    @Autowired
    EntityManager entityManager;

    private Jogador criar_jogador(JogadorRequestDTO joga_teste_dto)
    {
        Jogador jogadorTeste = new Jogador(joga_teste_dto);
        this.entityManager.persist(jogadorTeste);
        return jogadorTeste;
    }
}