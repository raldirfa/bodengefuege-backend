package de.hsosnabrueck.bodengefuegeapp.model.classes;


public class Grade {
    private double longitute;
    private double latitude;

    public Grade(double _longitude, double _latitude) {
        this.longitute = _longitude;
        this.latitude = _latitude;
    }

    public double getLongitude() {
        return this.longitute;
    }

    public double getLatitude() {
        return this.latitude;
    }

}
