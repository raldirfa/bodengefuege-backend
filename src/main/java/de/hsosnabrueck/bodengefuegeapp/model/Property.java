package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int rating;

    @Lob
    private byte[] image;

    @ManyToOne()
    private Sample sample;

    public Property() {
    }
}
