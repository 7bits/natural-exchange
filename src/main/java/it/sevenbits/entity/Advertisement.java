package it.sevenbits.entity;

import it.sevenbits.util.TimeManager;

/**
 *Class, which presents object Advertisement
 */
public class Advertisement {

    /**
     * Sorting field name
     */
    public static final String TITLE_COLUMN_CODE = "title";
    /**
     * Sorting field name
     */
    public static final String CREATED_DATE_COLUMN_CODE = "createdDate";

    private String title;
    private String text;
    private String photoFile;
    private Long createdDate;
    private Long updatedDate;
    private Boolean isDeleted;
    private User user;
    private Category category;

    /**
     * Constructor by default
     */
    public Advertisement() {
        createdDate = TimeManager.getTime();
        updatedDate = 0L;
        isDeleted = false;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getPhotoFile() {
        return photoFile;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public Boolean getIs_deleted() {
        return isDeleted;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setPhotoFile(final String photoFile) {
        this.photoFile = photoFile;
    }

    public void setCreatedDate(final Long createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(final Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setIs_deleted(final Boolean deleted) {
        isDeleted = deleted;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    /**
     * Show advertisement created date for users timezone.
     * @return String with date.
     */
    public String getCreatedDateFormat() {
        return TimeManager.getDateString(createdDate);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Advertisement that = (Advertisement) o;

        if (!category.equals(that.category)) {
            return false;
        }
        if (!createdDate.equals(that.createdDate)) {
            return false;
        }
        if (!isDeleted.equals(that.isDeleted)) {
            return false;
        }
        if (!photoFile.equals(that.photoFile)) {
            return false;
        }
        if (!text.equals(that.text)) {
            return false;
        }
        if (!title.equals(that.title)) {
            return false;
        }
        if (!updatedDate.equals(that.updatedDate)) {
            return false;
        }
        if (!user.equals(that.user)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + photoFile.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + updatedDate.hashCode();
        result = 31 * result + isDeleted.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}