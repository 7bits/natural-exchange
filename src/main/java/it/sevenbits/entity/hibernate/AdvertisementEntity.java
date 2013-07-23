package it.sevenbits.entity.hibernate;

import javax.persistence.*;

/**
 *Class, which presents Advertisement entity for Hibernate
 */
@Entity
@Table(name = "advertisement")
public class AdvertisementEntity extends it.sevenbits.entity.Advertisement {
    //private Long userId;

    private Long id;
    private CategoryEntity categoryEntity;

    public AdvertisementEntity() {
        super();
        //userId = 0L;
        //categoryId = 0L;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

//    @Transient
//    public Long getUserId() {
//        return userId;
//    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "title",length = 200, nullable = false)
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Column(name = "text",length = 1000, nullable = false)
    @Override
    public String getText() {
        return super.getText();
    }

    @Column(length = 255, name = "photo_file", nullable = false)
    @Override
    public String getPhotoFile() {
        return super.getPhotoFile();
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

//    public void setUserId(final Long userId) {
//        this.userId = userId;
//    }

    public void setCategoryEntity(final CategoryEntity _category) {
        this.categoryEntity = _category;
        super.setCategory(_category);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
