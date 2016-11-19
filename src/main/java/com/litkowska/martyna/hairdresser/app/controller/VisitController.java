package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.dto.VisitDTO;
import com.litkowska.martyna.hairdresser.app.dto.VisitProposalDTO;
import com.litkowska.martyna.hairdresser.app.model.Client;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.model.Visit;
import com.litkowska.martyna.hairdresser.app.security.models.AuthRole;
import com.litkowska.martyna.hairdresser.app.service.ClientService;
import com.litkowska.martyna.hairdresser.app.service.UserService;
import com.litkowska.martyna.hairdresser.app.service.VisitService;
import com.litkowska.martyna.hairdresser.app.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
public class VisitController {
    @Autowired
    private VisitService visitService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/visits", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllVisits() {
        try {
            List<Visit> visits = (List<Visit>) visitService.findAll();
            if (visits.size() == 0) {
                return new ResponseEntity<>("no visits found in database", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(visits, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/visits/{userId}", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllVisits(@PathVariable("userId") final long userId) {
        try {
            Client client = clientService.findOne(userId);
            List<Visit> visits = new ArrayList<>();
            if (client != null) {
                visits = client.getVisits();
            }
            if (visits.size() == 0) {
                return new ResponseEntity<>("no visits found in database", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(visits, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/visits/{hairdresser}", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllHaidresserVisits(@PathVariable("hairdresser") long hairdresserId) {
        try {
            List<Visit> visits = (List<Visit>) visitService.findVisitsByHairdresser(hairdresserId);
            if (visits.size() == 0) {
                return new ResponseEntity<>("no visits for hairdresser found in database", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(visits, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
        try {
            List<Visit> availableVisits = (List<Visit>) visitService.findAvailableVisits(
                    hairdresserId,
                    date,
                    hairServiceId);
            List<VisitProposalDTO> availableVisitsDTO = availableVisits.stream()
                    .map(visit -> new VisitProposalDTO(visit)).collect(Collectors.toList());
            if (availableVisits.size() == 0) {
                return new ResponseEntity<>("no available visits for this parameters", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(availableVisitsDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/visits/reserve", method = RequestMethod.POST)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllAvailableVisits(final @RequestBody VisitDTO visitDTO) {
        try {
            Visit savedVisit = visitService.reserveVisit(visitDTO);
            if (savedVisit != null) {
                emailSender.setVisit(savedVisit);
                emailSender.sendEmailReservation();
                return new ResponseEntity<>(savedVisit, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("could not reserve visit", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException | MessagingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "visits/cancel/{visitId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> cancelVisit(@PathVariable("visitId") final long visitId) {
        try {
            User user = userService.getCurrentLoggedUser();
            if (user.getRole() == AuthRole.ADMIN || checkClientCancel(user, visitId)
                    || checkHairdresserCancel(user, visitId)) {
                Visit visit = visitService.findOne(visitId);
                emailSender.setVisit(visit);
                visitService.cancelVisit(visitId);
                emailSender.sendEmailCancelReservation();
                return new ResponseEntity<>("the visit: " + visitId + " has been canceled", HttpStatus.OK);

            }
            return new ResponseEntity<>("could not cancel visit: " + visitId, HttpStatus.BAD_REQUEST);

        } catch (RuntimeException | MessagingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean checkClientCancel(final User user, final long visitId) {

        return user.getRole() == AuthRole.USER
                && visitService.checkIfClientCanCancel(visitId, clientService.findByUser(user).getId());
    }

    private boolean checkHairdresserCancel(final User user, final long visitId) {

        return user.getRole() == AuthRole.HAIRDRESSER
                && visitService.checkIfHairdresserCanCancel(visitId, clientService.findByUser(user).getId());
    }
}
