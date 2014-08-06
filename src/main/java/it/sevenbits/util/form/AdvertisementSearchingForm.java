package it.sevenbits.util.form;

import it.sevenbits.entity.Category;

/**
 * Class for advertisements searching spring form
 */
public class AdvertisementSearchingForm {
    private String[] categories;
    private String keyWords;
    private String dateFrom;
    private String dateTo;

    public AdvertisementSearchingForm() {
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(final String[] categories) {
        this.categories = categories;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(final String keyWords) {
        this.keyWords = keyWords;
    }

    public void setAll() {
        setCategories(new String[]{Category.NAME_CLOTHES, Category.NAME_GAMES, Category.NAME_NOT_CLOTHES});
        setDateFrom("");
        setDateTo("");
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
}