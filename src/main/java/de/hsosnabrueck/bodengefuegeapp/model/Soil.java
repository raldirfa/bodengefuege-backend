package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="soilSeq", allocationSize = 1)
public class Soil {

    @Id
    @GeneratedValue(generator="soilSeq")
    private long id;

    private String name;

    public Soil() {
    }

    public Soil(String name) {
        this.name = name;
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
