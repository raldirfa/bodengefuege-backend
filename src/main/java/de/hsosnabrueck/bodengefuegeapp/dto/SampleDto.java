package de.hsosnabrueck.bodengefuegeapp.dto;

import de.hsosnabrueck.bodengefuegeapp.model.Crop;

import java.util.List;

public class SampleDto {
    private Long id;
    private String localDateTime;
    private List<PropertyDto> properties;
    private Crop crop;
    private double lng;
    private double lat;

    public SampleDto() {
    }

    public SampleDto(Long id, String localDateTime, List<PropertyDto> properties, Crop crop, double lng, double lat) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.properties = properties;
        this.crop = crop;
        this.lng = lng;
        this.lat = lat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
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
