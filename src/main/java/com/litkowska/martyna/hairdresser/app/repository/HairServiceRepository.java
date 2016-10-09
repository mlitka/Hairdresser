package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.HairService;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Martyna on 06.10.2016.
 */
public interface HairServiceRepository extends PagingAndSortingRepository<HairService, Long> {
}
