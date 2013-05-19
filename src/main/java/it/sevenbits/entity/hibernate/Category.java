package it.sevenbits.entity.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "Category")
public class Category extends it.sevenbits.entity.Category {

    private Long id;

    public Category() {
        super();
    }

    public Category(final String name, final String description, final Long updatedDate, final Long createdDate, final Boolean deleted) {
        super(name,description,updatedDate,createdDate,deleted);
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

    @Column(name = "name",length = 200, nullable = false)
    @Override
    public String getName() {
        return super.getName();
    }

    @Column(name = "description",length = 200, nullable = false)
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Column(name = "created_date",nullable = false)
    @Override
    public Long getCreatedDate() {
        return super.getCreatedDate();
    }

    @Column(name = "updated_date",nullable = false)
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
