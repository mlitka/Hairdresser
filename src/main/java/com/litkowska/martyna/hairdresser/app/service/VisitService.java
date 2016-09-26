package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.Visit;
import com.litkowska.martyna.hairdresser.app.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Martyna on 21.09.2016.
 */
@Service
public class VisitService {
    @Autowired
    private VisitRepository visitRepository;

    public Iterable<Visit> finsAll(){
        return visitRepository.findAll();
    }
}
