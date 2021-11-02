package java.de.hsosnabrueck.bodengefuegeapp.model

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// SOURCE(s):
// https://attacomsian.com/blog/spring-data-jpa-one-to-one-mapping
// https://www.baeldung.com/spring-data-jpa-generate-db-schema

@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    @OneToMany(mappedBy = "sample", cascade = CascadeType.ALL)
    private List<Sample> sample = new ArrayList<>();

    public Field(String _name) {
        this.name = _name;
    }

    public long getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    } 

    public User getUser() {
        return this.user;
    }

    public List<Sample> getSample() {
        return this.sample;
    }

    public String getSamplesFormatted() {
        String samples;
        for (Sample s : this.sample) {
            samples += s.toString();
        }
        return samples;
    }

    public String setName(String _name) {
        this.name = _name;
    }

    @Override
    public String toString() {
        return "Field [id=" + this.id + ", name=" + this.name + 
                    ", samples=" + this.getSamplesFormatted() + "]";
    }
}