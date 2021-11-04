package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="gradeSeq")
public class Grade {

    @Id
    @GeneratedValue(generator = "gradeSeq")
    private long id;

    private double longitude;
    private double latitude;

    public Grade() {
    }

    public Grade(double _longitude, double _latitude) {
        this.longitude = _longitude;
        this.latitude = _latitude;
    }

    // Getter and Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
