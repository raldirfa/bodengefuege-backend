package de.hsosnabrueck.bodengefuegeapp.model.classes;

public enum TypeCriteria {

    TYPE1("Struktur der Oberfläche"),
    TYPE2("Durchwurzelung des Bodens"),
    TYPE3("Makroporen / Bioporen"),
    TYPE4("Gefüge und Verfestigung"),
    TYPE5("Organische Reststoffe"),
    TYPE6("Farbe und Geruch");

    private String type;

    private TypeCriteria(String _type) {
        this.type = _type;
    }

    public String getType() {
        return this.type;
    }  
}
