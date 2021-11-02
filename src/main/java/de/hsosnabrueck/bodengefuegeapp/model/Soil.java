package java.de.hsosnabrueck.bodengefuegeapp.model

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
    private String type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(mappedBy = "soil")
    private Sample sample;

    public Soil(String _type) {
        this.type = _type;
    }
}