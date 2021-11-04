package de.hsosnabrueck.bodengefuegeapp.dto;

import de.hsosnabrueck.bodengefuegeapp.model.Crop;
import de.hsosnabrueck.bodengefuegeapp.model.Field;
import de.hsosnabrueck.bodengefuegeapp.model.Grade;
import de.hsosnabrueck.bodengefuegeapp.model.Property;

import java.time.LocalDateTime;
import java.util.List;

public class SampleDto {
    private long field;
    private LocalDateTime date;
    private List<Property> properties;
    private long crop;
    private Grade grade;

    public SampleDto() {
    }

    public long getField() {
        return field;
    }

    public void setField(long field) {
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

    public long getCrop() {
        return crop;
    }

    public void setCrop(long crop) {
        this.crop = crop;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
