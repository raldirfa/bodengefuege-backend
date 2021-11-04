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

    // Getter and Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
