package de.hsosnabrueck.bodengefuegeapp.repository;

import de.hsosnabrueck.bodengefuegeapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface CropRepository extends CrudRepository<User, Long> {

}
