package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Criteria {

    @Id
    @GeneratedValue
    private long id;

    private String name;
}
