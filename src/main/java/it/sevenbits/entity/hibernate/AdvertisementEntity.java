package it.sevenbits.entity.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *Class, which presents Advertisement entity for Hibernate
 */
@Entity
@Table(name = "advertisement")
public class AdvertisementEntity extends it.sevenbits.entity.Advertisement {
    /*private Long userId;
        private Long categoryId;*/

    public AdvertisementEntity() {
        super();
        /*userId = 0L;
        categoryId = 0L;*/
    }

    /*@Transient
    public Long getCategoryId() {
        return categoryId;
    }

    @Transient
    public Long getUserId() {
        return userId;
    }*/

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return super.getId();
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

    /*public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    } */
}
