package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;

@Entity
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    @ManyToOne
    private Field field;

    public Sample() {
    }

    // Getter


    public long getId() {
        return Id;
    }

    public Field getField() {
        return field;
    }
}
