package org.launchcode.capstoneprojectjm.models.Data;

import org.launchcode.capstoneprojectjm.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AddressDao extends CrudRepository<Category, Integer> {
}
