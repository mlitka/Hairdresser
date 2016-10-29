package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.Hairdresser;
import com.litkowska.martyna.hairdresser.app.model.Shift;
import com.litkowska.martyna.hairdresser.app.repository.HaidresserRepository;
import com.litkowska.martyna.hairdresser.app.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

/**
 * Created by Martyna on 21.09.2016.
 */
@Service
public class HairdresserService {
    @Autowired
    private HaidresserRepository haidresserRepository;
    @Autowired
    private ShiftRepository shiftRepository;

    public Iterable<Hairdresser> findAll() {
        return haidresserRepository.findAll();
    }

    @Transactional
    public Hairdresser saveNewHairdresser(final Hairdresser hairdresser) {
        LocalTime shiftStart = LocalTime.of(8, 0);
        LocalTime shiftEnd = LocalTime.of(18, 0);
        Shift shift = shiftRepository.findByShiftStartAndShiftEnd(shiftStart, shiftEnd);
        if (shift == null) {
            shift = new Shift();
            shift.setShiftStart(LocalTime.of(8, 0));
            shift.setShiftEnd(LocalTime.of(18, 0));
            shiftRepository.save(shift);
        }
        hairdresser.setShift(shift);
        return haidresserRepository.save(hairdresser);
    }
}
