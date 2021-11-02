package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// SOURCE(s):
// https://attacomsian.com/blog/spring-data-jpa-one-to-one-mapping
// https://www.javainuse.com/spring/SpringBoot_DataJPA
// https://springframework.guru/one-to-one-relationship-in-jpa/

@Entity
public class Soil {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private SoilType type;
    @OneToOne(mappedBy = "sample", fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private Sample sample;

    public Soil() {}

    public Soil(SoilType _type) {
        this.type = _type;
    }
}