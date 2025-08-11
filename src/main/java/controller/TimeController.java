package controller;


import mapper.TimeMapper;
import model.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.TimeServices;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    TimeServices timeServices;
    TimeMapper timeMapper;


  /*  public ResponseEntity<Times> cadastrarTime()
    {
        Times timesalvo = timeServices.equal
    }


    @GetMapping("/{id}/buscar")
    @DeleteMapping("/{id}/delete")
    @PostMapping

*/

}
