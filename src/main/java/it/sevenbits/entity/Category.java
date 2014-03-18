package it.sevenbits.entity;

/**
 * Class for cateroty
 */
public class Category {

    /**
     * Category name
    */
    public static final String NAME_CLOTHES = "clothes";
    /**
     * Category name
    */
    public static final String NAME_GAMES = "games";
    /**
     * Category name
    */
    public static final String NAME_NOT_CLOTHES = "notclothes";

    private String name;
    private String description;
    private Long createdDate;
    private Long updatedDate;
    private Boolean isDeleted;

    public Category() {
    }

    public Category(final String name, final String description, final Long updatedDate, final Long createdDate, final Boolean deleted) {
        this.name = name;
        this.description = description;
        this.updatedDate = updatedDate;
        this.createdDate = createdDate;
        isDeleted = deleted;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final Boolean deleted) {
        isDeleted = deleted;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;

        if (!createdDate.equals(category.createdDate)) {
            return false;
        }
        if (!description.equals(category.description)) {
            return false;
        }
        if (!isDeleted.equals(category.isDeleted)) {
            return false;
        }
        if (!name.equals(category.name)) {
            return false;
        }
        if (!updatedDate.equals(category.updatedDate)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + updatedDate.hashCode();
        result = 31 * result + isDeleted.hashCode();
        return result;
    }

}
