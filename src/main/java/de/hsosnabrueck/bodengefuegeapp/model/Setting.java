package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// SOURCE(s):
// https://attacomsian.com/blog/spring-data-jpa-one-to-one-mapping
// https://www.javainuse.com/spring/SpringBoot_DataJPA

@Entity
public class Setting {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean tutorial;
    private boolean location;
    private boolean sync;
    private boolean voice_control;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private User user;

    public Setting() {
        this.tutorial = true;
        this.location = true;
        this.sync = true;
        this.voice_control = true;
    }

    public long getId() {
        return this.id;
    }

    public boolean getTutorial() {
        return this.tutorial;
    }

    public boolean getLocation() {
        return this.location;
    }

    public boolean getSync() {
        return this.sync;
    }

    public boolean getVoiceControl() {
        return this.voice_control;
    }

    public void setTutorial(boolean _tutorial) {
        this.tutorial = _tutorial;
    }

    public void setLocation(boolean _location) {
        this.location = _location;
    }

    public void setSync(boolean _sync) {
        this.sync = _sync;
    }

    public void setVoiceControl(boolean _voice_control) {
        this.voice_control = _voice_control;
    }

    @Override
    public String toString() {
        return "Setting [id=" + this.id + ", tutorial=" + this.tutorial + ", location=" + this.location + ", sync=" + this.sync + ", voice_control=" + this.voice_control + "]";
    }
}