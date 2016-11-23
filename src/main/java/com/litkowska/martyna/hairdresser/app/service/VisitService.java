package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.dto.VisitDTO;
import com.litkowska.martyna.hairdresser.app.model.*;
import com.litkowska.martyna.hairdresser.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    public Iterable<Visit> findAll() {
        return visitRepository.findAll();
    }

    public Visit findOne(final long visitId) {
        return visitRepository.findOne(visitId);
    }

    public Iterable<Visit> findAvailableVisits(final long hairdresserId, final LocalDate date, final long hairServiceId) {
//        System.out.println("\n\nDATE: " + date);
        Hairdresser hairdresser = haidresserRepository.findOne(hairdresserId);
//        System.out.println("\n\nHAIDRESSER: " + hairdresser);
        Shift shift = hairdresser.getShift();
        HairService hairService = hairServiceRepository.findOne(hairServiceId);
//        System.out.println("\n\nHAIR SERVICE: " + hairService);
        ArrayList<Visit> hairdressersVisitsOfTheDay = (ArrayList<Visit>) visitRepository.findByHairdresserAndDate(hairdresser, date);
//        System.out.println("\n\nVISITS OF THE DAY: " + hairdressersVisitsOfTheDay);
        LinkedList<Visit> availableVisits = new LinkedList<>();
        for (LocalTime availableTime = shift.getShiftStart(); availableTime.isBefore(shift.getShiftEnd()); availableTime = availableTime.plusMinutes(60)) {
//            System.out.println("\n\nCHECKING TIME: " + availableTime.toString());
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
    public Visit reserveVisit(final VisitDTO visitDTO) {
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

    public Iterable<Visit> findVisitsByHairdresser(final long hairdresserId) {
        Hairdresser hairdresser = haidresserRepository.findOne(hairdresserId);
        return visitRepository.findByHairdresser(hairdresser);
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

    @Transactional
    private Client saveClient(final VisitDTO visitDTO) {
        User user = userRepository.findByUsername(visitDTO.getClient().getEmail());
        Client client;
        if (user == null) {
            user = new User();
            user.setEmail(visitDTO.getClient().getEmail());
            user.setLastName(visitDTO.getClient().getLastName());
            user.setFirstName(visitDTO.getClient().getFirstName());
            user.setPhoneNo(visitDTO.getClient().getPhoneNo());
            client = new Client();
            if (user.checkNotNull()) {
                client.setUser(user);
                clientRepository.save(client);
            }
        } else {
            client = clientRepository.findByUser(user);
        }
        return client;
    }

    @Transactional
    public boolean cancelVisit(final long visitId) {
        Visit visit = visitRepository.findOne(visitId);
        if (visit != null) {
            Client client = visit.getClient();
            client.getVisits().remove(visit);
            clientRepository.save(client);
            visitRepository.delete(visit);
            return true;
        }
        return false;
    }

    public boolean checkIfCanCancel(final Visit visit) {
        LocalDateTime localDateTime = LocalDateTime.of(visit.getDate(), visit.getTime());
        return localDateTime.minusHours(24).isBefore(LocalDateTime.now());
    }

    /**
     * Client can cancel reservation max 24 hours before the visit
     *
     * @param visitId
     * @param clientId
     * @return
     */
    public boolean checkIfClientCanCancel(final long visitId, final long clientId) {
        Visit visit = visitRepository.findOne(visitId);
        if (visit != null && visit.getClient().getId() == clientId) {
            if (checkTimeOfCanceling(visit)) {
                cancelVisit(visitId);
                return true;
            }
        }
        return false;
    }

    private boolean checkTimeOfCanceling(final Visit visit) {
        return LocalDateTime.of(visit.getDate(), visit.getTime()).minusHours(24).isBefore(LocalDateTime.now());
    }

    public boolean checkIfHairdresserCanCancel(final long visitId, final long hairdresserId) {
        Visit visit = visitRepository.findOne(visitId);
        if (visit != null && visit.getHairdresser().getId() == hairdresserId) {
            if (checkTimeOfCanceling(visit)) {
                cancelVisit(visitId);
                return true;
            }
        }
        return false;
    }

}
