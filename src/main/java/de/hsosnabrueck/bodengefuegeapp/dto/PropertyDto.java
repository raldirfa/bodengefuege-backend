package de.hsosnabrueck.bodengefuegeapp.dto;

public class PropertyDto {
    private int rating;
    private byte[] image;
    private String criteria;

    public PropertyDto() {
    }

    public PropertyDto(int rating, byte[] image, String criteria) {
        this.rating = rating;
        this.image = image;
        this.criteria = criteria;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
