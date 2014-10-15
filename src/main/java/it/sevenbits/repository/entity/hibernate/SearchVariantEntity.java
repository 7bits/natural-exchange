package it.sevenbits.repository.entity.hibernate;

import it.sevenbits.repository.entity.Category;
import it.sevenbits.repository.entity.SearchVariant;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * Search variant entity class for hibernate
 */
@Entity
@Table(name = "searchVariant")
public class SearchVariantEntity extends SearchVariant {

    private Long id;

    Set<CategoryEntity> categories;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "searchVariant_category", joinColumns = {
            @JoinColumn(name = "search_variant_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "category_id",
                    nullable = false, updatable = false) })
    @Fetch(FetchMode.JOIN)
    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public SearchVariantEntity() {
        super();
    }

    public SearchVariantEntity(final String email, final String keyWords, final Set<CategoryEntity> categories) {
        super(email, keyWords);
        this.setCategories(categories);
    }

    public String stringOfCategoryNames() {
        String result = "";
        for (Category category: categories) {
            result += category.getName() + " | ";
        }
        if (result.length() > 0) {
            result = StringUtils.removeEnd(result, " | ");
        }
        return StringUtils.trim(result);
    }

    public String stringOfCategorySlugs() {
        String result = "";
        for (Category category: categories) {
            result += category.getSlug() + " ";
        }
        return StringUtils.trim(result);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "email", length = 200, nullable = false)
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(final String email) {
        super.setEmail(email);
    }

    @Column(name = "key_words", length = 200, nullable = false)
    @Override
    public String getKeyWords() {
        return super.getKeyWords();
    }

    @Override
    public void setKeyWords(final String keyWords) {
        super.setKeyWords(keyWords);
    }

//    @Column(name = "categories", length = 200, nullable = false)
//    @Override
//    public String getCategories() {
//        return super.getCategories();
//    }

//    @Override
//    public void setCategories(final String categories) {
//        super.setCategories(categories);
//    }

    @Override
    public void setCreatedDate(final Long createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Column(name = "created_date", length = 200, nullable = false)
    @Override
    public Long getCreatedDate() {
        return super.getCreatedDate();
    }
}
