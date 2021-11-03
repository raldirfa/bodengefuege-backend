package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="criteriaSeq")
public class Criteria {

    @Id
    @GeneratedValue(generator="criteriaSeq")
    private long id;

    private String name;
}
