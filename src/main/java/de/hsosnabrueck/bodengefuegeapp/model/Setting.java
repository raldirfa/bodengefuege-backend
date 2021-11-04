package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "settingSeq", allocationSize = 1)
public class Setting {

    @Id
    @GeneratedValue(generator = "settingSeq")
    private long id;

    private boolean tutorial = true;
    private boolean location = true;
    private boolean sync = true;

    public Setting() {
    }

    // Getter and Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isTutorial() {
        return tutorial;
    }

    public void setTutorial(boolean tutorial) {
        this.tutorial = tutorial;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
