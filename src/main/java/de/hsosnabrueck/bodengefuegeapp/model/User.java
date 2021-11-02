package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.util.List;

// https://thorben-janssen.com/key-jpa-hibernate-annotations/#ManyToOne_and_OneToMany

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Setting setting;

    @OneToMany(mappedBy = "user")
    private List<Field> fields;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.setting = new Setting();
    }

    public List<Field> getFields() {
        return fields;
    }
}
