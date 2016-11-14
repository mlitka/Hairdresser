package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.dto.HairdresserDTO;
import com.litkowska.martyna.hairdresser.app.model.Hairdresser;
import com.litkowska.martyna.hairdresser.app.service.HairdresserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
public class HairdresserController {
    @Autowired
    private HairdresserService hairdresserService;

    @RequestMapping(value = "/hairdressers", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllHairdressers(){
        List<Hairdresser> hairdressers = (List<Hairdresser>) hairdresserService.findAll();
        List<HairdresserDTO> hairdressersDTO = hairdressers.stream().map(hairdresser -> new HairdresserDTO(hairdresser)).collect(Collectors.toList());
//        hairdressers.forEach(hairdresser -> hairdressersDTO.add(new HairdresserDTO(hairdresser)));
        if(hairdressersDTO.size()==0){
            return new ResponseEntity<>("no hairdressers found in database", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hairdressersDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/hairdresser", method = RequestMethod.POST)
    @CrossOrigin("*")
    public ResponseEntity<?> saveHaidresser(final @RequestBody Hairdresser hairdresser){
        try{
            Hairdresser savedHairdresser = hairdresserService.saveNewHairdresser(hairdresser);
            if(savedHairdresser!=null){
                return new ResponseEntity<Hairdresser>(savedHairdresser, HttpStatus.CREATED);
            }
            return new ResponseEntity<String>("could not save hairdresser", HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
