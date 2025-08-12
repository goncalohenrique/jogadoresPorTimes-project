package com.goncalo.jogadores.repository;

import com.goncalo.jogadores.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
}
