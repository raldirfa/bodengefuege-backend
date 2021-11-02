package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// SOURCE(s):
// https://attacomsian.com/blog/spring-data-jpa-one-to-one-mapping
// https://www.baeldung.com/spring-data-jpa-generate-db-schema

@Entity
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private CropType type;
    @OneToOne(mappedBy = "favorite_crop", cascade = CascadeType.ALL)
    @JoinColumn(name = "favorite_crop_id");
    private Favorite_Crop favorite_crop;

    public Crop() {}
    
    public Crop(CropType _type) {
        this.type = _type;
    }

    public CropType getType() {
        return this.type;
    }

    public void setType(CropType _type) {
        this.type = _type;
    }

    @Override
    public String toString() {
        return "Crop [type=" + this.type + "]";
    }
}
