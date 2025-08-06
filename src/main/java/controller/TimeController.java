package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.TimeServices;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    TimeServices timeServices;
/*
    public ResponseEntity<Time> cadastrarTime()
    {
        Time timesalvo = timeServices
    }*/


}
