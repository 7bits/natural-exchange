package it.sevenbits.entity.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *Класс, представляющий сущность таблицы Advertisement в БД
 */
@Entity
@Table(name = "advertisement")
public class Advertisement extends it.sevenbits.entity.Advertisement {
    /*private Long userId;
        private Long categoryId;*/
    private Long id;

    public Advertisement() {
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
        return id;
    }

    @Column(length = 200, nullable = false)
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Column(length = 1000, nullable = false)
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

    public void setId(Long id) {
        this.id = id;
    }

    /*public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    } */
}
