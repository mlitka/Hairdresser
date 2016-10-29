package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.HairdresserLevel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna on 06.10.2016.
 */
@Repository
public interface HairdresserLevelRepository extends PagingAndSortingRepository<HairdresserLevel, Long> {
}
