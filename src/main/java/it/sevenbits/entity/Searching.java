package it.sevenbits.entity;

import java.util.Arrays;


/**
 * Helping class
 */
public class Searching{

    private String keyWords;
    private String[] categories;
    private String allCategories;

    public Searching(final SearchVariant searchVariant){
        this.keyWords = searchVariant.getKeyWords();
        this.allCategories = searchVariant.getCategories();
        this.categories = (searchVariant.getCategories()).split(" ");
    }

    public Searching() {
        this.keyWords = null;
        this.categories = null;
        this.allCategories = null;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getAllCategories() {
        return allCategories;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public void setCategories(String[] categories) {
        this.categories  = categories.clone();
    }

    public void setAllCategories(String allCategories) {
        this.allCategories = allCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Searching searching = (Searching) o;

        if (allCategories != null ? !allCategories.equals(searching.allCategories) : searching.allCategories != null)
            return false;
        if (keyWords != null ? !keyWords.equals(searching.keyWords) : searching.keyWords != null) return false;
        int length = this.categories.length;
        if (((Searching) o).categories.length != length)
            return false;
        int i,j;
        // if (!Arrays.equals(categories, searching.categories)) return false;
        for (i = 0; i < length; i++) {
            for (j = 0; j< length; j++) {
                if (categories[i].equals(((Searching) o).categories[j])) {
                    break;
                }
            }
            if (j == length) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = keyWords != null ? keyWords.hashCode() : 0;
        result = 31 * result + (categories != null ? Arrays.hashCode(categories) : 0);
        result = 31 * result + (allCategories != null ? allCategories.hashCode() : 0);
        return result;
    }
}
