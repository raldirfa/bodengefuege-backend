package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    @ManyToOne
    private Field field;

    private LocalDateTime date;

    @OneToMany(mappedBy = "sample")
    private List<Property> properties;

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
