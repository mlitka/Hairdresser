package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.HairService;
import com.litkowska.martyna.hairdresser.app.model.Hairdresser;
import com.litkowska.martyna.hairdresser.app.model.Shift;
import com.litkowska.martyna.hairdresser.app.model.Visit;
import com.litkowska.martyna.hairdresser.app.repository.HaidresserRepository;
import com.litkowska.martyna.hairdresser.app.repository.HairServiceRepository;
import com.litkowska.martyna.hairdresser.app.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Iterable<Visit> findAll(){
        return visitRepository.findAll();
    }

    public Iterable<Visit> findAvailableVisits(final long hairdresserId, final LocalDate date, final long hairServiceId){
        System.out.println("\n\nDATE: "+date);
        Hairdresser hairdresser = haidresserRepository.findOne(hairdresserId);
        System.out.println("\n\nHAIDRESSER: "+hairdresser);
        Shift shift = hairdresser.getShift();
        HairService hairService = hairServiceRepository.findOne(hairServiceId);
        System.out.println("\n\nHAIR SERVICE: "+hairService);
        ArrayList<Visit> hairdressersVisitsOfTheDay = (ArrayList<Visit>) visitRepository.findByHairdresserAndDate(hairdresser, date);
        System.out.println("\n\nVISITS OF THE DAY: "+hairdressersVisitsOfTheDay);
        LinkedList<Visit> availableVisits = new LinkedList<>();
        for(LocalTime availableTime = shift.getShiftStart(); availableTime.isBefore(shift.getShiftEnd()); availableTime = availableTime.plusMinutes(60)){
            System.out.println("\n\nCHECKING TIME: "+availableTime.toString());
            if(!isTimeColliding(hairdressersVisitsOfTheDay, availableTime, hairService.getDuration())){
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

    private boolean isTimeColliding(final List<Visit> visitList, final LocalTime newVisitTime, final int duration){
        boolean isCollliding = false;
        for(Visit visit: visitList){
            if(visit.getTime().equals(newVisitTime)){
                isCollliding = true;
            } else if(newVisitTime.isBefore(visit.getTime().plusMinutes(duration))){
                isCollliding = true;
            }

        }
        return isCollliding;
    }
}
