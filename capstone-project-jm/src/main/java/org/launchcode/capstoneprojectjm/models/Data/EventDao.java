package org.launchcode.capstoneprojectjm.models.Data;

import org.launchcode.capstoneprojectjm.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EventDao extends CrudRepository<Event, Integer> {
}
