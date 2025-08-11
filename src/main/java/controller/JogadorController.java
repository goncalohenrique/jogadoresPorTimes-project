package controller;

import dto.JogadorRequestDTO;
import dto.JogadorResponseDTO;
import jakarta.validation.Valid;
import mapper.JogadorMapper;
import model.Jogador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.JogadoresServices;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogador")
public class JogadorController {


    private JogadoresServices jogadoresServices;
    private JogadorMapper jogadorMapper;

    @Autowired
    public JogadorController(JogadoresServices jogadoresServices, JogadorMapper jogadorMapper) {
        this.jogadoresServices = jogadoresServices;
        this.jogadorMapper = jogadorMapper;
    }

    @PostMapping
    public ResponseEntity<JogadorResponseDTO> cadastrar_Jogador(@RequestBody @Valid JogadorRequestDTO jogdto) {
        Jogador jogador_salvo = jogadoresServices.cadastrar_jogador(jogdto);
        JogadorResponseDTO dto = jogadorMapper.jogador_para_dto(jogador_salvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public  ResponseEntity<List<JogadorResponseDTO>> buscar_todos()
    {
        return ResponseEntity.ok(jogadoresServices.buscar_todos_jogadores());
    }

    @GetMapping("/{id_jogador}")
    public  ResponseEntity<JogadorResponseDTO> buscar_porId(@PathVariable Long id_jogador)
    {
        Optional<JogadorResponseDTO> jogad_dto_optional = jogadoresServices.buscar_jogad_porId(id_jogador);
        return jogad_dto_optional.map(ResponseEntity::ok) // se presente, retorna 200 OK com o DTO
                .orElseGet(()-> ResponseEntity.notFound().build()); // se vazio, retorna 404
    }

    @PostMapping("/{id_joga}/atualizar")
    public ResponseEntity<JogadorResponseDTO> atualizar_jogador(@PathVariable Long id_joga, @RequestBody @Valid JogadorRequestDTO jogadto )
    {
        Jogador jogador_atualizado = jogadoresServices.atualizar_jogador_porId(id_joga, jogadto);
        JogadorResponseDTO dto = jogadorMapper.jogador_para_dto(jogador_atualizado);
        return  ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{id_jog}")
    public void deletar_jogador(@PathVariable Long id_jog)
    {
        jogadoresServices.deletar_jogador_porId(id_jog);
    }


}
