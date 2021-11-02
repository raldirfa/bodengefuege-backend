package de.hsosnabrueck.bodengefuegeapp.repository;

import de.hsosnabrueck.bodengefuegeapp.model.Sample;
import org.springframework.data.repository.CrudRepository;

public interface SampleRepository extends CrudRepository<Sample, Long> {
}
