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
public class Sample {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "soil_id")
    private Soil soil;

    public Sample() {
        // Source(s):
        // https://www.munonye.com/working-with-date-formats-and-conversion-in-spring-boot-and-angular/
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date now = new Date();
        this.date = LocalDateTime.parse(now, formatter);
    }

    public long getID() {
        return this.id;
    }

    public Soil getSoil() {
        return this.soil;
    }
}