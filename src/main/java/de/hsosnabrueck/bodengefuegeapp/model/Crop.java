package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="cropSeq", allocationSize = 1)
public class Crop {

    @Id
    @GeneratedValue(generator = "cropSeq")
    private long id;

    private String name;

    public Crop() {
    }

    public Crop(String name) {
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
