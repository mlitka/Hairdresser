package com.litkowska.martyna.hairdresser.app.repository;

import com.litkowska.martyna.hairdresser.app.model.Visit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna on 21.09.2016.
 */
@Repository
public interface VisitRepository extends PagingAndSortingRepository<Visit, Long> {
}
