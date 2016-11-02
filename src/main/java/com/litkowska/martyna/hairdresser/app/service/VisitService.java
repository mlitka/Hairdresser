package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.dto.VisitDTO;
import com.litkowska.martyna.hairdresser.app.model.*;
import com.litkowska.martyna.hairdresser.app.repository.ClientRepository;
import com.litkowska.martyna.hairdresser.app.repository.HaidresserRepository;
import com.litkowska.martyna.hairdresser.app.repository.HairServiceRepository;
import com.litkowska.martyna.hairdresser.app.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@Service
public class VisitService {
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private HaidresserRepository haidresserRepository;
    @Autowired
    private HairServiceRepository hairServiceRepository;
    @Autowired
    private ClientRepository clientRepository;

    public Iterable<Visit> findAll() {
        return visitRepository.findAll();
    }

    public Iterable<Visit> findAvailableVisits(final long hairdresserId, final LocalDate date, final long hairServiceId) {
        System.out.println("\n\nDATE: " + date);
        Hairdresser hairdresser = haidresserRepository.findOne(hairdresserId);
        System.out.println("\n\nHAIDRESSER: " + hairdresser);
        Shift shift = hairdresser.getShift();
        HairService hairService = hairServiceRepository.findOne(hairServiceId);
        System.out.println("\n\nHAIR SERVICE: " + hairService);
        ArrayList<Visit> hairdressersVisitsOfTheDay = (ArrayList<Visit>) visitRepository.findByHairdresserAndDate(hairdresser, date);
        System.out.println("\n\nVISITS OF THE DAY: " + hairdressersVisitsOfTheDay);
        LinkedList<Visit> availableVisits = new LinkedList<>();
        for (LocalTime availableTime = shift.getShiftStart(); availableTime.isBefore(shift.getShiftEnd()); availableTime = availableTime.plusMinutes(60)) {
            System.out.println("\n\nCHECKING TIME: " + availableTime.toString());
            if (!isTimeColliding(hairdressersVisitsOfTheDay, availableTime, hairService.getDuration())
                    && !isCollidingWithShift(shift, availableTime, hairService)) {
                Visit availableVisit = new Visit();
                availableVisit.setDate(date);
                availableVisit.setTime(availableTime);
                availableVisit.setHairdresser(hairdresser);
                availableVisit.setHairService(hairService);
                availableVisits.add(availableVisit);
            }
        }

        return availableVisits;
    }

    @Transactional
    public Visit reserveVisit(final VisitDTO visitDTO){
        Visit visit = new Visit();
        Client client = this.saveClient(visitDTO);
        visit.setHairdresser(haidresserRepository.findOne(visitDTO.getHairdresserId()));
        visit.setHairService(hairServiceRepository.findOne(visitDTO.getHairServiceId()));
        visit.setDate(LocalDate.parse(visitDTO.getDate()));
        visit.setTime(LocalTime.parse(visitDTO.getTime()));
        client.getVisits().add(visit);
        clientRepository.save(client);
        return visitRepository.save(visit);
    }

    private boolean isTimeColliding(final List<Visit> visitList, final LocalTime newVisitTime, final int duration) {
        boolean isCollliding = false;
        for (Visit visit : visitList) {
            if (visit.getTime().equals(newVisitTime) || newVisitTime.isBefore(visit.getTime().plusMinutes(duration))) {
                isCollliding = true;
                break;
            }
        }
        return isCollliding;
    }

    private boolean isCollidingWithShift(final Shift shift, final LocalTime time, final HairService hairService) {
        return time.plusMinutes(hairService.getDuration()).isAfter(shift.getShiftEnd());
    }

    private Client saveClient(final VisitDTO visitDTO){
        Client client = clientRepository.findByEmail(visitDTO.getClient().getEmail());
        if(client==null){
            client = new Client();
            client.setEmail(visitDTO.getClient().getEmail());
            client.setLastName(visitDTO.getClient().getLastName());
            client.setFirstName(visitDTO.getClient().getFirstName());
            client.setPhoneNo(visitDTO.getClient().getPhoneNo());
            clientRepository.save(client);
        }
        return client;
    }
}
