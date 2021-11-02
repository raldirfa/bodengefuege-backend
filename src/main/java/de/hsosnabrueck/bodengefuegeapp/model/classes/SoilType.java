package de.hsosnabrueck.bodengefuegeapp.model.classes;


public enum SoilType {

    SAND("Sand"),
    SCHLUFF("Schluff"),
    TON("Ton"),
    LEHM("Lehm");

    private String type;

    private SoilType(String _type) {
        this.type = _type;
    }

    public String getType() {
        return this.type;
    } 
}
