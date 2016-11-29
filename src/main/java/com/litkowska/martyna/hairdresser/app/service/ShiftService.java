package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.Shift;
import com.litkowska.martyna.hairdresser.app.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * Created by Martyna on 19.11.2016.
 */
@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    public Shift saveShift(final LocalTime shiftStart, final LocalTime shiftEnd){
        Shift shift = shiftRepository.findByShiftStartAndShiftEnd(shiftStart, shiftEnd);
        if(shift==null){
            shift = new Shift();
            shift.setShiftStart(shiftStart);
            shift.setShiftEnd(shiftEnd);
            return shiftRepository.save(shift);
        }
        return shift;
    }
}
