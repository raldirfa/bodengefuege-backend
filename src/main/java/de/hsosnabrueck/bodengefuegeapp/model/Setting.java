package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private boolean tutorial;
    private boolean location;
    private boolean sync;

    public Setting() {
    }

}
