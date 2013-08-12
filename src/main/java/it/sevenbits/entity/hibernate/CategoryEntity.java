package it.sevenbits.entity.hibernate;


import it.sevenbits.entity.Category;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Category entity class for hibernate
 */
@Entity
@Table(name = "Category")
public class CategoryEntity extends it.sevenbits.entity.Category {

    private Long id;
    private Set<AdvertisementEntity> advertisements;

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

    public CategoryEntity(final Category category) {
        super(
                category.getName(), category.getDescription(), category.getUpdatedDate(),
                category.getCreatedDate(), category.getIsDeleted()
        );
    }

    public CategoryEntity(
            final String name, final String description, final Long updatedDate,
            final Long createdDate, final Boolean deleted
    ) {
        super(name, description, updatedDate, createdDate, deleted);
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
