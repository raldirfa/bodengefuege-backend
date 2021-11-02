package java.de.hsosnabrueck.bodengefuegeapp.model

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// SOURCE(s):
// https://attacomsian.com/blog/spring-data-jpa-one-to-one-mapping
// https://www.baeldung.com/spring-data-jpa-generate-db-schema

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String eMail;
    private String password;
    private String jvtToken;
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    @JoinColumn(name = "setting_id");
    private Setting setting;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Field> field = new ArrayList<>();

    public User(boolean _tutorial, boolean _location, boolean _sync, boolean _voice_control) {
        this.tutorial = _tutorial;
        this.location = _location;
        this.sync = _sync;
        this.voice_control = _voice_control;
    }


    public long getID() {
        return this.id;
    }

    public String getEmail() {
        return this.eMail;
    }

    public String getPassword() {
        return this.password;
    }

    public String getJvtToken() {
        return this.jvtToken;
    }

    public Setting getSetting() {
        return this.setting;
    }

    public List<Field> getField() {
        return this.field;
    }

    public String getFieldsFormatted() {
        String fields;
        for (Field f : this.field) {
            fields += f.toString();
        }
        return fields;
    }

    public void setEmail(String _email) {
        this.eMail = _email.toLowerString(); 
    }

    public void setPassword(String _password) {
        this.password = _password;
    }

    public void setJvtToken(String _token) {
        this.jvtToken = _token;
    }

    @Override
    public String toString() {
        return "User [id=" + this.id + ", email=" + this.eMail + ", jvt_token=" + this.jvt_token + 
                        this.setting.toString() + ", Fields=" + this.getFieldsFormatted() + "]";
    }


}


