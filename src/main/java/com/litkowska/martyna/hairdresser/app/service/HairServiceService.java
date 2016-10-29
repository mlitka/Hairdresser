package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.HairService;
import com.litkowska.martyna.hairdresser.app.repository.HairServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Martyna on 26.10.2016.
 */
@Service
public class HairServiceService {
    @Autowired
    private HairServiceRepository hairServiceRepository;

    public Iterable<HairService> findAll(){
        return hairServiceRepository.findAll();
    }
}
