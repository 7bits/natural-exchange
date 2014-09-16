package it.sevenbits.entity;

import it.sevenbits.dao.CategoryDao;
import it.sevenbits.util.TimeManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *Class, which presents object Advertisement
 */
@Component
public class SearchVariant {

    @Autowired
    private CategoryDao categoryDao;

    private String email;
    private String keyWords;
    private String categories;
    private Long createdDate;
    /**
     * Constructor by default
     */
    public SearchVariant() {
        createdDate = TimeManager.getTime();
    }

    public SearchVariant(final String email, final String keyWords, final String categories) {
        this.email = email;
        this.keyWords = keyWords;
        this.categories = categories;
        TimeZone timeZone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timeZone);
        createdDate = calendar.getTimeInMillis();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(final String keyWords) {
        this.keyWords = keyWords;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(final String categories) {
        this.categories = categories;
    }

    public void setCreatedDate(final Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedDate() {
        return createdDate;
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

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchVariant that = (SearchVariant) o;

        if (!categories.equals(that.categories)) {
            return false;
        }
        if (!createdDate.equals(that.createdDate)) {
            return false;
        }
        if (!email.equals(that.email)) {
            return false;
        }
        if (!keyWords.equals(that.keyWords)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + keyWords.hashCode();
        result = 31 * result + categories.hashCode();
        result = 31 * result + createdDate.hashCode();
        return result;
    }
}