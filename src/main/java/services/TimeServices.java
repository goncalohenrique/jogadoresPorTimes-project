package services;

import dto.TimeRequestDTO;
import model.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repository.TimeRepository;

@Service
public class TimeServices {

    @Autowired
    TimeRepository timeRepository;

}
