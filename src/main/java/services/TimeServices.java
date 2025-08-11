package services;

import dto.JogadorResponseDTO;
import dto.TimeRequestDTO;
import dto.TimeResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import mapper.TimeMapper;
import model.Jogador;
import model.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TimeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TimeServices {

    @Autowired
    TimeRepository timeRepository;
    TimeMapper timeMapper;

    public Times cadastrar_times(TimeRequestDTO timedto)
    {
        Times time_cadastrado = new Times();
        time_cadastrado.setNome(timedto.getNome());
        time_cadastrado.setIdtime(timedto.getIdtime());

        return timeRepository.save(time_cadastrado);
    }

    public List<TimeResponseDTO> buscar_todos_times()
    {
        List<Times> times = timeRepository.findAll();
        return times.stream()
                .map(timeMapper:: time_para_dto)
                .collect(Collectors.toList());
    }

    public Optional<TimeResponseDTO> buscar_time_porId(Long idTime)
    {
        Optional<Times> time = timeRepository.findById(idTime);
       return time.map(timeMapper::time_para_dto);
    }


    public void deletar_time_porId(Long id_time)
    {
        Times time = timeRepository.findById(id_time)
                .orElseThrow(()-> new EntityNotFoundException("Time não encontrado!"));
        timeRepository.delete(time);
    }


}
