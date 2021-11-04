package de.hsosnabrueck.bodengefuegeapp.repository;

import de.hsosnabrueck.bodengefuegeapp.model.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Long> {
}
