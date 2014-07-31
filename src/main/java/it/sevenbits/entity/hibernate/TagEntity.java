package it.sevenbits.entity.hibernate;

import it.sevenbits.entity.Tag;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
public class TagEntity extends Tag {
    private Long id;


    private AdvertisementEntity advertisement;


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "name", length = 100, nullable = false)
    @Override
    public String getName() {
        return super.getName();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "advertisement_id", nullable = false)
    public AdvertisementEntity getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementEntity advertisement) {
        this.advertisement = advertisement;
    }
}
