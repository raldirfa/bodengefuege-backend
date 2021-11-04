package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.util.List;

// https://thorben-janssen.com/key-jpa-hibernate-annotations/#ManyToOne_and_OneToMany

@Entity
@SequenceGenerator(name="userSeq")
public class User {

    @Id
    @GeneratedValue(generator = "userSeq")
    private Long id;

    private String email;

    private String username;

    private String password;

    private String jwtToken;

    @OneToOne(cascade = CascadeType.ALL)
    private Setting setting;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Field> fields;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Crop> favCrops;

    public User() {
    }

    public User(String username,String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.setting = new Setting();
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Crop> getFavCrops() {
        return favCrops;
    }

    public void setFavCrops(List<Crop> favCrops) {
        this.favCrops = favCrops;
    }
}
