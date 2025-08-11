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
import services.TimeServices;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
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


  /*  public ResponseEntity<Times> cadastrarTime()


    @GetMapping("/{id}/buscar")
    @DeleteMapping("/{id}/delete")
    @PostMapping

*/

}
