package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.Hairdresser;
import com.litkowska.martyna.hairdresser.app.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna on 21.09.2016.
 */
@Repository
public interface HaidresserRepository extends PagingAndSortingRepository<Hairdresser, Long> {
    Hairdresser findByUser(final User user);
}
