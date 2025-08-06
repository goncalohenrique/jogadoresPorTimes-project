package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TimeRepository;

@Service
public class TimeServices {

    @Autowired
    TimeRepository timeRepository;

}
