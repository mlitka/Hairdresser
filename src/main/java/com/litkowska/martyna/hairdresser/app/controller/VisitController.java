package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.dto.VisitDTO;
import com.litkowska.martyna.hairdresser.app.dto.VisitProposalDTO;
import com.litkowska.martyna.hairdresser.app.model.Visit;
import com.litkowska.martyna.hairdresser.app.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
public class VisitController {
    @Autowired
    private VisitService visitService;

    @RequestMapping(value = "/visits", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllVisits() {
        List<Visit> visits = (List<Visit>) visitService.findAll();
        return new ResponseEntity<List<Visit>>(visits, HttpStatus.OK);
    }

    @RequestMapping(value = "/visits/{hairdresser}/available/{service}", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllAvailableVisits(
            @PathVariable("hairdresser") long hairdresserId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable("service") long hairServiceId
    ) {
        System.out.println("\n\n" + hairdresserId);
        System.out.println("\n\n" + date);
        System.out.println("\n\n" + hairServiceId);
        List<Visit> availableVisits = (List<Visit>) visitService.findAvailableVisits(
                hairdresserId,
                date,
                hairServiceId);
        List<VisitProposalDTO> availableVisitsDTO = availableVisits.stream().map(visit -> new VisitProposalDTO(visit)).collect(Collectors.toList());
        return new ResponseEntity<>(availableVisitsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/visit/reserve", method = RequestMethod.POST)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllAvailableVisits(final @RequestBody VisitDTO visitDTO){
        try{
            Visit savedVisit = visitService.reserveVisit(visitDTO);
            if(savedVisit!=null){
                return new ResponseEntity<>(savedVisit, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("could not reserve visit", HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
