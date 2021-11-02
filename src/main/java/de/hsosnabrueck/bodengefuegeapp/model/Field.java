package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "field")
    private List<Sample> samples;

    public Field() {
    }

    public Field(User user) {
        this.user = user;
    }

}
