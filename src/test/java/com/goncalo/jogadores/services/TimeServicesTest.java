package com.goncalo.jogadores.services;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.goncalo.jogadores.dto.TimeRequestDTO;
import com.goncalo.jogadores.model.Times;
import com.goncalo.jogadores.repository.TimeRepository;

import jakarta.persistence.EntityManager;

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
        TimeRequestDTO timeDto = new TimeRequestDTO("Corinthians");
        Times timeCriado = this.criar_time(timeDto);
        Long idTimeTeste = timeCriado.getIdtime();

        Optional<Times> resultado = timeRepository.findById(idTimeTeste);
        assertThat(resultado.isPresent()).isTrue();
        assertThat(resultado.get().getNome()).isEqualTo("Corinthians");
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
        Times timeSalvo = timeRepository.save(timeTeste);
        this.entityManager.flush();
        return timeSalvo;
    }

}