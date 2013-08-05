package it.sevenbits.entity.hibernate;

import it.sevenbits.entity.SearchVariant;

import javax.persistence.*;

@Entity
@Table(name = "searchVariant")
public class SearchVariantEntity extends SearchVariant {

    private Long id;

    public SearchVariantEntity() {
        super();
    }

    public SearchVariantEntity(String email, String keyWords, String categories) {
        super(email, keyWords, categories);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "email",length = 200, nullable = false)
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Column(name = "key_words",length = 200, nullable = false)
    @Override
    public String getKeyWords() {
        return super.getKeyWords();
    }

    @Override
    public void setKeyWords(String keyWords) {
        super.setKeyWords(keyWords);
    }

    @Column(name = "categories",length = 200, nullable = false)
    @Override
    public String getCategories() {
        return super.getCategories();
    }

    @Override
    public void setCategories(String categories) {
        super.setCategories(categories);
    }

    @Override
    public void setCreatedDate(Long createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    public Long getCreatedDate() {
        return super.getCreatedDate();
    }
}
