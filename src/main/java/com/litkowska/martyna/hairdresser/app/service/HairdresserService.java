package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.Hairdresser;
import com.litkowska.martyna.hairdresser.app.model.Shift;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.repository.HaidresserRepository;
import com.litkowska.martyna.hairdresser.app.repository.ShiftRepository;
import com.litkowska.martyna.hairdresser.app.repository.UserRepository;
import com.litkowska.martyna.hairdresser.app.security.models.AuthRole;
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
    @Autowired
    private ShiftService shiftService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public Iterable<Hairdresser> findAll() {
        return haidresserRepository.findAll();
    }

    @Transactional
    public Hairdresser saveNewHairdresser(final Hairdresser hairdresser) {
        if (checkNotNull(hairdresser)) {
            hairdresser.getUser().setRole(AuthRole.HAIRDRESSER);
            haidresserRepository.save(hairdresser);
            Shift shift = shiftRepository.findByShiftStartAndShiftEnd(hairdresser.getShift().getShiftStart(),
                    hairdresser.getShift().getShiftEnd());
            if (shift == null && checkShift(hairdresser.getShift())) {
                shiftRepository.save(hairdresser.getShift());
            }
            return haidresserRepository.save(hairdresser);
        }
        return null;
    }

    @Transactional
    public Hairdresser upgradeUser(final String username) {
        User user = upgradeUserToHairdresser(username);
        if (user != null) {
            Hairdresser hairdresser = new Hairdresser();
            hairdresser.setUser(user);
            Shift shift = shiftService.saveShift(LocalTime.of(8,0),
                    LocalTime.of(18,0));
            if (shift != null) {
                hairdresser.setShift(shift);
                return haidresserRepository.save(hairdresser);
            }
        }
        return null;
    }

    public User upgradeUserToHairdresser(final String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRole(AuthRole.HAIRDRESSER);
            return userRepository.save(user);
        }
        return null;
    }

    public boolean isUserAHairdresser(final String username) {
        User user = userRepository.findByUsername(username);
        System.out.println("isUserHaidr");
        System.out.println(user);
        return haidresserRepository.findByUser(user) != null;
    }

    private boolean checkNotNull(final Hairdresser hairdresser) {
        return hairdresser.getUser().checkNotNull()
                && hairdresser.getUser().checkPassword() && hairdresser.getShift() != null;
    }

    private boolean checkShift(final Shift shift) {
        return shift.getShiftStart() != null && shift.getShiftEnd() != null;
    }


}
