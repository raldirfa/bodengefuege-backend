package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.util.List;

// https://thorben-janssen.com/key-jpa-hibernate-annotations/#ManyToOne_and_OneToMany

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    private String username;

    private String email;

    private String password;

    private String jwtToken;

    @OneToOne(cascade = CascadeType.ALL)
    private Setting setting;

    @OneToMany(mappedBy = "user")
    private List<Field> fields;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.setting = new Setting();
    }

    // Getter

    public long getId() {
        return Id;
    }

    public String getUsername() {
        return username;
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
