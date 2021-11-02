// IMG Speichern
// mit Sample und Criteria verlinken
package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;

// SOURCE(s):
// https://attacomsian.com/blog/spring-data-jpa-one-to-one-mapping
// https://www.baeldung.com/spring-data-jpa-generate-db-schema

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int rating;
    private byte[] img;
    @OneToOne(mappedBy = "criteria", cascade = CascadeType.ALL)
    @JoinColumn(name = "criteria_id");
    private List<Criteria> criteria = new ArrayList<>();

    public Property() {}

    public Property(int _rating, byte[] _img) {
        this.rating = _rating;
        this.img = _img;
    }

    public Property(int _rating) {
        this(_rating, null);
    }

    public long getID() {
        return this.id;
    }

    public int getRating() {
        return this.rating;
    }

    public byte[] getImg() {
        return this.img;
    }

    public List<Criteria> getCriteria() {
        return this.criteria;
    }

    public void setRating(int _rating) {
        this.rating = _rating;
    }

    public void setImg(byte[] _img) {
        this.img = _img;
    }
}