package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.model.Visit;
import com.litkowska.martyna.hairdresser.app.service.VisitService;
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
public class VisitController {
    @Autowired
    private VisitService visitService;

    @RequestMapping("/visits")
    public ResponseEntity<?> getAllVisits(){
        List<Visit> visits = (List<Visit>) visitService.finsAll();
        return new ResponseEntity<List<Visit>>(visits, HttpStatus.OK);
    }
}
