package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.dto.HairdresserDTO;
import com.litkowska.martyna.hairdresser.app.model.Hairdresser;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.repository.UpgradeHairdresserDTO;
import com.litkowska.martyna.hairdresser.app.security.models.AuthRole;
import com.litkowska.martyna.hairdresser.app.service.HairdresserService;
import com.litkowska.martyna.hairdresser.app.service.UserService;
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
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/hairdressers", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllHairdressers(){
        List<Hairdresser> hairdressers = (List<Hairdresser>) hairdresserService.findAll();
        if(hairdressers.size()==0){
            return new ResponseEntity<>("no hairdressers found in database", HttpStatus.NOT_FOUND);
        }
        List<HairdresserDTO> hairdressersDTO = hairdressers.stream()
                .map(hairdresser -> new HairdresserDTO(hairdresser)).collect(Collectors.toList());
        return new ResponseEntity<>(hairdressersDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/hairdresser", method = RequestMethod.POST)
    @CrossOrigin("*")
    public ResponseEntity<?> saveHaidresser(final @RequestBody Hairdresser hairdresser){
        try{
            Hairdresser savedHairdresser = hairdresserService.saveNewHairdresser(hairdresser);
            if(savedHairdresser!=null){
                return new ResponseEntity<>(savedHairdresser, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("could not save hairdresser", HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * this method gves an opportunity to upgrade user account into hairdresser
     * @param upgradeHairdresserDTO
     * @return
     */
    @RequestMapping(value = "/hairdresser", method = RequestMethod.POST)
    @CrossOrigin("*")
    public ResponseEntity<?> addHaidresserUsername(@RequestBody UpgradeHairdresserDTO upgradeHairdresserDTO){
        try{
            User user = userService.getCurrentLoggedUser();
            if(user.getRole()== AuthRole.ADMIN
                    && !hairdresserService.isUserAHairdresser(upgradeHairdresserDTO.getUsername())){
                Hairdresser hairdresser = hairdresserService.upgradeUser(upgradeHairdresserDTO);
                return new ResponseEntity<>(hairdresser, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("could not upgrade user: "+upgradeHairdresserDTO.getUsername(), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
