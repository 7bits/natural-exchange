package it.sevenbits.web.util.form.advertisement;

import it.sevenbits.repository.dao.CategoryDao;
import it.sevenbits.repository.entity.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class for advertisements searching spring form
 */
@Component
public class AdvertisementSearchingForm {

    // Removed dao
    @Autowired
    private CategoryDao categoryDao;

    private Boolean isDeleted;
    private Integer currentPage;
    private String currentCategory;
    private String keyWords;
    private String dateFrom;
    private String dateTo;

    public AdvertisementSearchingForm() {
    }


    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(final String keyWords) {
        this.keyWords = keyWords;
    }

    // remove work with dao
    public void setAll() {
        List<Category> categoryList = this.categoryDao.findAll();
        this.setCurrentCategory(StringUtils.join(categoryList, " "));
        this.setDateFrom("");
        this.setDateTo("");
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Integer getRealCurrentPage(final int pageCount) {
        if (this.getCurrentPage() == null || this.getCurrentPage() > pageCount
            || this.getCurrentPage() == 0) {
            return 1;
        } else {
            return currentPage;
        }
    }
}