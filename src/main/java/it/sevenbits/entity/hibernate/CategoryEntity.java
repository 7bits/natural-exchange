package it.sevenbits.entity.hibernate;


import javax.persistence.*;
import java.util.Set;

/**
 * Category entity class for hibernate
 */
@Entity
@Table(name = "Category")
public class CategoryEntity extends it.sevenbits.entity.Category {

    private Long id;
    private Set<AdvertisementEntity> advertisements;
//    private Set<SearchVariantEntity> searches;
//
//    public Set<SearchVariantEntity> getSearches() {
//        return searches;
//    }
//
//    public void setSearches(Set<SearchVariantEntity> searches) {
//        this.searches = searches;
//    }


    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    public Set<AdvertisementEntity> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(final Set<AdvertisementEntity> advertisements) {
        this.advertisements = advertisements;
    }

    public CategoryEntity() {
        super();
    }

    public CategoryEntity(
            final String slug, final String name, final String description, final Long updatedDate,
            final Long createdDate, final Boolean deleted
    ) {
        super(slug, name, description, updatedDate, createdDate, deleted);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "slug", length = 200, nullable = false)
    @Override
    public String getSlug() {
        return super.getSlug();
    }

    @Column(name = "name", length = 200, nullable = false)
    @Override
    public String getName() {
        return super.getName();
    }

    @Column(name = "description", length = 200, nullable = false)
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Column(name = "created_date", nullable = false)
    @Override
    public Long getCreatedDate() {
        return super.getCreatedDate();
    }

    @Column(name = "updated_date", nullable = false)
    @Override
    public Long getUpdatedDate() {
        return super.getUpdatedDate();
    }

    @Column(name = "is_deleted", nullable = false)
    @Override
    public Boolean getIsDeleted() {
        return super.getIsDeleted();
    }

}
