package it.sevenbits.util.form;

import it.sevenbits.entity.hibernate.CategoryEntity;

import java.util.Set;

public class CurrentSearchForm {
    private String keywords;
    private Set<CategoryEntity> category;
    private Long searchVariantId;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public Long getSearchVariantId() {
        return searchVariantId;
    }

    public void setSearchVariantId(Long searchVariantId) {
        this.searchVariantId = searchVariantId;
    }

    public Set<CategoryEntity> getCategory() {
        return category;
    }

    public void setCategory(Set<CategoryEntity> category) {
        this.category = category;
    }
}
