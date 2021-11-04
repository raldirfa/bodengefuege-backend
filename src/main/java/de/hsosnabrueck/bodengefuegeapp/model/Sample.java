package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@SequenceGenerator(name="sampleSeq")
public class Sample {

    @Id
    @GeneratedValue(generator = "sampleSeq")
    private long id;

    @ManyToOne
    private Field field;

    private LocalDateTime date;

    @OneToMany(mappedBy = "sample", cascade = CascadeType.ALL)
    private List<Property> properties;

    @OneToOne
    private Crop crop;

    @OneToOne(cascade = CascadeType.ALL)
    private Grade grade;

    public Sample() {
    }

    public Sample(Field field, LocalDateTime date, List<Property> properties, Crop crop, Grade grade) {
        this.field = field;
        this.date = date;
        this.properties = properties;
        this.crop = crop;
        this.grade = grade;
    }

    // Getter and Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
