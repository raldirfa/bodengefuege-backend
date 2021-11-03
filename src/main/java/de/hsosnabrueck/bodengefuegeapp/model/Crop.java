package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="cropSeq")
public class Crop {

    @Id
    @GeneratedValue(generator = "cropSeq")
    private long id;

    private String name;
}
