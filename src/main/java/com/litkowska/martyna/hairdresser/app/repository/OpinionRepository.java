package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.Client;
import com.litkowska.martyna.hairdresser.app.model.Opinion;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna on 28.10.2016.
 */
@Repository
public interface OpinionRepository extends PagingAndSortingRepository<Opinion, Long> {
    Opinion findByClient(final Client client);
}
