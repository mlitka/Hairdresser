package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.model.Hairdresser;
import com.litkowska.martyna.hairdresser.app.service.HairdresserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
public class HairdresserController {
    @Autowired
    private HairdresserService hairdresserService;

    @RequestMapping("/hairdressers")
    public ResponseEntity<?> getAllHairdressers(){
        List<Hairdresser> clients = (List<Hairdresser>) hairdresserService.findAll();
        return new ResponseEntity<List<Hairdresser>>(clients, HttpStatus.OK);
    }
}
