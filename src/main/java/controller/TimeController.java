package controller;


import model.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.TimeServices;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    TimeServices timeServices;

  /*  public ResponseEntity<Times> cadastrarTime()
    {
        Times timesalvo = timeServices.equal
    }
*/


}
