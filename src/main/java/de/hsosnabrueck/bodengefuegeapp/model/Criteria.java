package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// SOURCE(s):
// https://attacomsian.com/blog/spring-data-jpa-one-to-one-mapping
// https://www.baeldung.com/spring-data-jpa-generate-db-schema
// https://medium.com/javarevisited/an-effective-way-to-use-java-enums-in-your-spring-application-485c969794a8

@Entity
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private TypeCriteria type;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private Property property;

    public Criteria() {}
    
    public Criteria(String _type) {
        this.type = _type
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Criteria [type=" + this.type + "]";
    }
}