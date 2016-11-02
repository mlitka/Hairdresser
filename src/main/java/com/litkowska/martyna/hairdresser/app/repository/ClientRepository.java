package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna on 21.09.2016.
 */
@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
    Client findByEmail(final String email);
}
