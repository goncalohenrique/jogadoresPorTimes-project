package com.goncalo.jogadores;

import com.goncalo.jogadores.dto.JogadorRequestDTO;
import com.goncalo.jogadores.model.Jogador;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.parser.Entity;

@SpringBootTest
@ActiveProfiles("test")
class JogadoresApplicationTests {

	@Autowired
	EntityManager entityManager;

	@Test
	void contextLoads() {

	}

	private Jogador criar_jogador(JogadorRequestDTO joga_teste_dto)
	{
		Jogador jogadorTeste = new Jogador(joga_teste_dto);
		this.entityManager.persist(jogadorTeste);
		return jogadorTeste;
	}


}
