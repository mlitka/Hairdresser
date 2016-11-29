package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.dto.VisitDTO;
import com.litkowska.martyna.hairdresser.app.model.*;
import com.litkowska.martyna.hairdresser.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private ClientService clientService;
    @Autowired
    private UserService userService;

    public Iterable<Visit> findAll() {
        return visitRepository.findAll();
    }

    public Visit findOne(final long visitId) {
        return visitRepository.findOne(visitId);
    }

    public Iterable<Visit> findAvailableVisits(final long hairdresserId, final LocalDate date, final long hairServiceId) {
        System.out.println(date.getDayOfWeek());
        System.out.println(date);
        if (!date.getDayOfWeek().toString().equals(DayOfWeek.SATURDAY.toString()) && !(date.getDayOfWeek().toString().equals(DayOfWeek.SUNDAY.toString()))) {
            Hairdresser hairdresser = haidresserRepository.findOne(hairdresserId);
            Shift shift = hairdresser.getShift();
            HairService hairService = hairServiceRepository.findOne(hairServiceId);
            ArrayList<Visit> hairdressersVisitsOfTheDay = (ArrayList<Visit>) visitRepository.findByHairdresserAndDate(hairdresser, date);
            System.out.println(hairdressersVisitsOfTheDay.size());
            LinkedList<Visit> availableVisits = new LinkedList<>();
            for (LocalTime availableTime = shift.getShiftStart(); availableTime.isBefore(shift.getShiftEnd()); availableTime = availableTime.plusMinutes(60)) {
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
        return new ArrayList<>();
    }

    @Transactional
    public Visit reserveVisit(final VisitDTO visitDTO) {
        Visit visit = new Visit();
        Client client = clientService.saveClient(visitDTO);
        visit.setHairdresser(haidresserRepository.findOne(visitDTO.getHairdresserId()));
        visit.setHairService(hairServiceRepository.findOne(visitDTO.getHairServiceId()));
        visit.setDate(LocalDate.parse(visitDTO.getDate()));
        visit.setTime(LocalTime.parse(visitDTO.getTime()));
        if (visit.getDate().getDayOfWeek() != DayOfWeek.SATURDAY && visit.getDate().getDayOfWeek() != DayOfWeek.SUNDAY) {
            client.getVisits().add(visit);
            clientService.save(client);
            visit.setClient(client);
            return visitRepository.save(visit);
        }
        return null;
    }

    public Iterable<Visit> findVisitsByHairdresser(final long hairdresserId) {
        Hairdresser hairdresser = haidresserRepository.findOne(hairdresserId);
        return visitRepository.findByHairdresser(hairdresser);
    }

    private boolean isTimeColliding(final List<Visit> visitList, final LocalTime newVisitTime, final int duration) {
        boolean isCollliding = false;
        for (Visit visit : visitList) {
            System.out.println(visit);
            System.out.println(newVisitTime);
            if (visit.getTime().equals(newVisitTime)
                    || (newVisitTime.isAfter(visit.getTime()) && newVisitTime.isBefore(visit.getTime().plusMinutes(visit.getHairService().getDuration())))
                    || (newVisitTime.isBefore(visit.getTime()) && newVisitTime.plusMinutes(duration).isAfter(visit.getTime()))){
                isCollliding = true;
                break;
            }
                System.out.println(false);
        }
        return isCollliding;
    }

    private boolean isCollidingWithShift(final Shift shift, final LocalTime time, final HairService hairService) {
        return time.plusMinutes(hairService.getDuration()).isAfter(shift.getShiftEnd());
    }


    @Transactional
    public boolean cancelVisit(final long visitId) {
        Visit visit = visitRepository.findOne(visitId);
        if (visit != null) {
            Client client = visit.getClient();
            client.getVisits().remove(visit);
            clientService.save(client);
            visitRepository.delete(visit);
            return true;
        }
        return false;
    }

    public boolean checkIfCanCancel(final Visit visit) {
        LocalDateTime localDateTime = LocalDateTime.of(visit.getDate(), visit.getTime());
        System.out.println("Checking time");
        System.out.println(localDateTime);
        System.out.println(LocalDateTime.now().plusHours(24));
        return LocalDateTime.now().plusHours(24).isBefore(localDateTime);
    }

    /**
     * Client can cancel reservation max 24 hours before the visit
     *
     * @param visitId
     * @param userId
     * @return
     */
    public boolean checkIfClientCanCancel(final long visitId, final long userId) {
        Visit visit = visitRepository.findOne(visitId);
        User user = userService.findOne(userId);
        System.out.println(user);
        if (user != null) {
            Client client = clientService.findByUser(user);
            System.out.println(client);
            System.out.println(visit);
            if (visit != null && visit.getClient().getClientId() == client.getClientId()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTimeOfCanceling(final Visit visit) {
        return LocalDateTime.now().plusHours(24).isBefore(LocalDateTime.of(visit.getDate(), visit.getTime()));
    }

    public boolean checkIfHairdresserCanCancel(final long visitId, final long hairdresserId) {
        Visit visit = visitRepository.findOne(visitId);
        if (visit != null && visit.getHairdresser().getHairdresserId() == hairdresserId) {
            return true;
        }
        return false;
    }

}
