package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna on 13.11.2016.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByEmail(final String email);
    User findByUsername(final String username);
    User findByUsernameAndPassword(final String usename, final String password);
}
