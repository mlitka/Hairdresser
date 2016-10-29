package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.model.HairService;
import com.litkowska.martyna.hairdresser.app.service.HairServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
public class HairServiceController {
    @Autowired
    private HairServiceService hairServiceService;

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllHairServices(){
        List<HairService> hairServices = (List<HairService>) hairServiceService.findAll();
        return new ResponseEntity<List<HairService>>(hairServices, HttpStatus.OK);
    }
}
