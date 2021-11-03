package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.util.List;

// https://thorben-janssen.com/key-jpa-hibernate-annotations/#ManyToOne_and_OneToMany

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private String email;

    private String password;

    private String jwtToken;

    @OneToOne(cascade = CascadeType.ALL)
    private Setting setting;

    @OneToMany(mappedBy = "user")
    private List<Field> fields;

    @ManyToMany
    private List<Crop> crops;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.setting = new Setting();
    }

    // Getter

    public long getId() {
        return Id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public Setting getSetting() {
        return setting;
    }

    public List<Field> getFields() {
        return fields;
    }
}
