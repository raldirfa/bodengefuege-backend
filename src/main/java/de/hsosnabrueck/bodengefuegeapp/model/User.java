package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.util.List;

// https://thorben-janssen.com/key-jpa-hibernate-annotations/#ManyToOne_and_OneToMany

@Entity
@SequenceGenerator(name="userSeq")
public class User {

    @Id
    @GeneratedValue(generator = "userSeq")
    private Long Id;

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

    // Getter

    public Long getId() {
        return Id;
    }

    public String getUsername(){
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

    public void setEmail(String email){
        this.email = email;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setSetting(Setting setting){
        this.setting = setting;
    }
}
