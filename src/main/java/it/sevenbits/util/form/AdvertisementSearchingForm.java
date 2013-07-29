package it.sevenbits.util.form;

public class AdvertisementSearchingForm {
    private String category;
    private String keyWords;

    public AdvertisementSearchingForm() {
        this.category = null;
        this.keyWords = null;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}