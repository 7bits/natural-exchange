package it.sevenbits.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *Class, which presents object Advertisement
 */
public class Advertisement {

    public static final String TITLE_COLUMN_CODE = "title";
    public static final String CREATED_DATE_COLUMN_CODE = "createdDate";

    private Long id;
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
        title = null;
        text = null;
        photoFile = null;
        TimeZone timeZone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timeZone);
        createdDate = calendar.getTimeInMillis();
        updatedDate = 0l;
        isDeleted = false;
    }

    public Long getId() {
        return id;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setIsDeleted(final Boolean deleted) {
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
     * @return String with date "hh:mm dd.MM.yyyy".
     */
    public String getCreatedDateFormat() {
        TimeZone timeZone = TimeZone.getDefault();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        dateFormat.setTimeZone(timeZone);
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(createdDate);
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Advertisement that = (Advertisement) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (photoFile != null ? !photoFile.equals(that.photoFile) : that.photoFile != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (updatedDate != null ? !updatedDate.equals(that.updatedDate) : that.updatedDate != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (photoFile != null ? photoFile.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

}