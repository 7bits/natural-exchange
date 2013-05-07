package it.sevenbits.entity;

public class Category {
    private Integer id;
    private String name;
    private String description;
    private Long createdDate;
    private Long updatedDate;
    private Boolean isDeleted;

    public Category(final Integer id, final String name, final String description, final Long updatedDate, final Long createdDate, final Boolean deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.updatedDate = updatedDate;
        this.createdDate = createdDate;
        isDeleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(final Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Category category = (Category) o;

        if (createdDate != null ? !createdDate.equals(category.createdDate) : category.createdDate != null)
            return false;
        if (description != null ? !description.equals(category.description) : category.description != null)
            return false;
        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (isDeleted != null ? !isDeleted.equals(category.isDeleted) : category.isDeleted != null) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (updatedDate != null ? !updatedDate.equals(category.updatedDate) : category.updatedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }
}
