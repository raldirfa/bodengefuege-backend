package de.hsosnabrueck.bodengefuegeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Favorite_Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private User user;
    @OneToOne(mappedBy = "crop", fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private Crop crop;
    private boolean favorite;
    @OneToOne(mappedBy = "sample", fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private Sample sample;

    public Favorite_Crop() {
        this.favorite = false;
    }

    public boolean getFavorite() {
        return this.favorite;
    }

    public void setFavorite(boolean _favorite) {
        this.favorite = _favorite;
    }
}
