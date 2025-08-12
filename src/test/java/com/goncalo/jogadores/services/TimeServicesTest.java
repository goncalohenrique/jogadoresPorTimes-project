package com.goncalo.jogadores.services;

import com.goncalo.jogadores.dto.TimeRequestDTO;
import com.goncalo.jogadores.model.Times;
import com.goncalo.jogadores.repository.TimeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TimeServicesTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TimeRepository timeRepository;

    @Test
    void cadastrar_times() {
    }

    @Test
    void buscar_todos_times() {
    }



    @Test
    @DisplayName("Deve encontrar o time com sucesso")
    void buscar_time_porIdSucces() {
        Long idTimeTeste= 1L;
        TimeRequestDTO timeDto = new TimeRequestDTO("Corinthians");
        this.criar_time(timeDto);

        Optional<Times> resultado = timeRepository.findById(idTimeTeste);
        assertThat(resultado.isPresent()).isTrue();
    }

    @Test
    void atualizar_times_porId() {
    }

    @Test
    void deletar_time_porId() {
    }

    private Times criar_time(TimeRequestDTO timeTesteDto)
    {
        Times timeTeste = new Times(timeTesteDto);
        this.entityManager.persist(timeTeste);
        return timeTeste;

    }

}