package it.sevenbits.web.util.form.advertisement;

public class SearchEditForm {
    private String keywords;
    private String category;
    private Long searchVariantId;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Long getSearchVariantId() {
        return searchVariantId;
    }

    public void setSearchVariantId(Long searchVariantId) {
        this.searchVariantId = searchVariantId;
    }
}
