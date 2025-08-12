package com.goncalo.jogadores.repository;

import com.goncalo.jogadores.model.Times;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Times, Long> {
}
