package de.hsosnabrueck.bodengefuegeapp.dto;

import de.hsosnabrueck.bodengefuegeapp.model.Crop;
import de.hsosnabrueck.bodengefuegeapp.model.Grade;

import java.time.LocalDateTime;
import java.util.List;

public class SampleDto {
    private LocalDateTime localDateTime;
    private List<PropertyDto> properties;
    private Crop crop;
    private double lng;
    private double lat;

    public SampleDto() {
    }

    public SampleDto(LocalDateTime localDateTime, List<PropertyDto> properties, Crop crop, double lng, double lat) {
        this.localDateTime = localDateTime;
        this.properties = properties;
        this.crop = crop;
        this.lng = lng;
        this.lat = lat;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public List<PropertyDto> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDto> properties) {
        this.properties = properties;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
