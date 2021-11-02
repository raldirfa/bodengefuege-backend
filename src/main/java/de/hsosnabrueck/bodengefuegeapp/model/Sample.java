package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;

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
    private Grade gps;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "soil_id")
    private Soil soil;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "field_id")
    private Field field;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "favorite_crop_id")
    private Favorite_Crop favorite_crop;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)  // MAXIMAL 8, WIE!?
    // @size() ???
    private List<Property> property = new ArrayList<>();

    public Sample() {
        // Source(s):
        // https://www.munonye.com/working-with-date-formats-and-conversion-in-spring-boot-and-angular/
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date now = new Date();
        this.date = LocalDateTime.parse(now, formatter);
    }

    public Sample(Grade _gps) {
        this();
        this.gps = _gps;
    }

    public long getID() {
        return this.id;
    }

    public Soil getSoil() {
        return this.soil;
    }

    public Grade() {
        return this.gps;
    }
}