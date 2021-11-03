package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="soilSeq")
public class Soil {

    @Id
    @GeneratedValue(generator="soilSeq")
    private long id;

    private String name;

    public Soil() {
    }
}
