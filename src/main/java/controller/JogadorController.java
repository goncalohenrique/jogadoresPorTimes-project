package controller;

import dto.JogadorRequestDTO;
import dto.JogadorResponseDTO;
import jakarta.validation.Valid;
import mapper.JogadorMapper;
import model.Jogador;
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

    @PostMapping
    public ResponseEntity<Jogador> cadastrar_Jogador(@RequestBody @Valid JogadorRequestDTO jogdto)
    {
        Jogador jogador_salvo = jogadoresServices.cadastrar_jogador(jogdto);
        return ResponseEntity.status(HttpStatus.CREATED).body(jogador_salvo);
    }

    @GetMapping
    public  ResponseEntity<List<JogadorResponseDTO>> buscar_todos()
    {
        return ResponseEntity.ok(jogadoresServices.buscar_todos_jogadores());
    }

    public  ResponseEntity<JogadorResponseDTO> buscar_porId(@PathVariable Long id_jogador)
    {
        Optional<JogadorResponseDTO> jogad_dto_optional = jogadoresServices.buscar_jogad_porId(id_jogador);
        return jogad_dto_optional.map(ResponseEntity::ok) // se presente, retorna 200 OK com o DTO
                .orElseGet(()-> ResponseEntity.notFound().build()); // se vazio, retorna 404
    }


}
