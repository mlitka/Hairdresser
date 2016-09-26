package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.Hairdresser;
import com.litkowska.martyna.hairdresser.app.repository.HaidresserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Martyna on 21.09.2016.
 */
@Service
public class HairdresserService {
    @Autowired
    private HaidresserRepository haidresserRepository;

    public Iterable<Hairdresser> findAll(){
        return haidresserRepository.findAll();
    }
}
