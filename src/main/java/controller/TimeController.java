package controller;
import dto.TimeRequestDTO;
import dto.TimeResponseDTO;
import jakarta.validation.Valid;
import mapper.TimeMapper;
import model.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.TimeServices;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/times")
public class TimeController {

    TimeServices timeServices;
    TimeMapper timeMapper;

    @Autowired
    public TimeController(TimeServices timeServices, TimeMapper timeMapper) {
        this.timeServices = timeServices;
        this.timeMapper = timeMapper;
    }

    @PostMapping
    public ResponseEntity<TimeResponseDTO> cadastrar_time(@RequestBody @Valid TimeRequestDTO time_dto)
    {
        Times time_salvo = timeServices.cadastrar_times(time_dto);
        //Transforms os atributos em dto:
        TimeResponseDTO dto = timeMapper.time_para_dto(time_salvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponseDTO>> buscar_todos()
    {
        return ResponseEntity.ok(timeServices.buscar_todos_times());
    }

    @GetMapping("/{id_time}")
    public ResponseEntity<TimeResponseDTO> buscar_poriD(@PathVariable Long id_time)
    {
        Optional<TimeResponseDTO> time_dto_opt = timeServices.buscar_time_porId(id_time);
        return  time_dto_opt.map(ResponseEntity::ok) // Se encontrar, retorna 200 OK com o DTO
                .orElseThrow(()-> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Jogador não encontrado!")); // Se vazio retorna error 404
    }

    @PutMapping("/{id_time}")
    public ResponseEntity<TimeResponseDTO> atualizar_porId(@PathVariable Long id_time, @RequestBody @Valid TimeRequestDTO dtoTime_atual)
    {
        Times time_atualizado = timeServices.atualizar_times_porId(id_time, dtoTime_atual);
        TimeResponseDTO time_atuali_dto = timeMapper.time_para_dto(time_atualizado);
        return ResponseEntity.ok(time_atuali_dto);
    }

    @DeleteMapping("/{id_time_del}")
    public ResponseEntity<Void> deletar_porID(@PathVariable Long id_time_del)
    {
        timeServices.deletar_time_porId(id_time_del);
        return ResponseEntity.noContent().build();
    }


}
