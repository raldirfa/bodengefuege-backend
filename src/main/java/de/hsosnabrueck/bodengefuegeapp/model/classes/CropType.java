package de.hsosnabrueck.bodengefuegeapp.model.classes;


public enum CropType {

    KARTOFFEL("Kartoffel"),
    ZUCKERUEBE("Zuckerrübe"),
    RAPS("Raps"),
    ROGGEN("Roggen"),
    GERSTE("Gerste"),
    WEIZEN("Weizen"),
    DINKEL("Dinkel"),
    EMMER("Emmer"),
    BUCHWEIZEN("Buchweizen"),
    SENF("Senf");

    private String type;

    private CropType(String _type) {
        this.type = _type;
    }

    public String getType() {
        return this.type;
    } 
}
