package it.sevenbits.util.form;

import it.sevenbits.entity.Category;

public class AdvertisementSearchingForm {
    private String[] categories;
    private String keyWords;

    public AdvertisementSearchingForm() {
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public void setAll() {
        setCategories(new String[]{Category.NAME_CLOTHES,Category.NAME_GAMES,Category.NAME_NOT_CLOTHES});
    }
}