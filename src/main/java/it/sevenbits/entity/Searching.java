package it.sevenbits.entity;

import java.util.Arrays;

/**
 * Helping class
 */
public class Searching{

    private String keyWords;
    private String[] categories;
    private String allCategory;

    public Searching() {
        this.keyWords = null;
        this.categories = null;
        this.allCategory = null;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories  = categories.clone();
    }

    public String getAllCategory() {
        return allCategory;
    }

    public void setAllCategory(String allCategory) {
        this.allCategory = allCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Searching searching = (Searching) o;

        if (allCategory != null ? !allCategory.equals(searching.allCategory) : searching.allCategory != null)
            return false;
        if (!Arrays.equals(categories, searching.categories)) return false;
        if (keyWords != null ? !keyWords.equals(searching.keyWords) : searching.keyWords != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keyWords != null ? keyWords.hashCode() : 0;
        result = 31 * result + (categories != null ? Arrays.hashCode(categories) : 0);
        result = 31 * result + (allCategory != null ? allCategory.hashCode() : 0);
        return result;
    }
}
