package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private String name;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Sample> samples;

    public Field() {
    }

    public Field(User user) {
        this.user = user;
    }

    // Getter

    public long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public List<Sample> getSamples() {
        return samples;
    }
}
