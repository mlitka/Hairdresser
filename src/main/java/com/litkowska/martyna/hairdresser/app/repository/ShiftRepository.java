package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.Shift;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

/**
 * Created by Martyna on 27.10.2016.
 */
@Repository
public interface ShiftRepository extends PagingAndSortingRepository<Shift, Long> {
    Shift findByShiftStartAndShiftEnd(final LocalTime shiftStart, final LocalTime shiftEnd);
}
