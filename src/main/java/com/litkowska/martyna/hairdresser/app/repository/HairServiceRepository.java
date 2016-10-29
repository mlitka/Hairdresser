package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.HairService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna on 06.10.2016.
 */
@Repository
public interface HairServiceRepository extends PagingAndSortingRepository<HairService, Long> {
}
