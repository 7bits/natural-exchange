package it.sevenbits.entity.hibernate;

import it.sevenbits.entity.SearchVariant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Search variant entity class for hibernate
 */
@Entity
@Table(name = "searchVariant")
public class SearchVariantEntity extends SearchVariant {

    private Long id;

    public SearchVariantEntity() {
        super();
    }

    public SearchVariantEntity(final String email, final String keyWords, final String categories) {
        super(email, keyWords, categories);
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

    @Column(name = "categories", length = 200, nullable = false)
    @Override
    public String getCategories() {
        return super.getCategories();
    }

    @Override
    public void setCategories(final String categories) {
        super.setCategories(categories);
    }

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
