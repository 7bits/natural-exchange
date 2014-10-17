package it.sevenbits.services;

import it.sevenbits.repository.dao.SearchVariantDao;
import it.sevenbits.repository.entity.SearchVariant;
import it.sevenbits.repository.entity.hibernate.CategoryEntity;
import it.sevenbits.repository.entity.hibernate.SearchVariantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SearchVariantService {
    @Autowired
    private SearchVariantDao searchVariantDao;

    public void createSearchVariant(final SearchVariantEntity searchVariantEntity, final Set<CategoryEntity> categoryEntities) {
        this.searchVariantDao.create(searchVariantEntity, categoryEntities);
    }

    public List<SearchVariantEntity> findByEmail(final String email) {
        return this.searchVariantDao.findByEmail(email);
    }

    public SearchVariantEntity findById(final Long id) {
        return this.searchVariantDao.findById(id);
    }

    public void delete(final SearchVariantEntity searchVariant) {
        this.searchVariantDao.delete(searchVariant);
    }

    public void update(final SearchVariantEntity searchVariantEntity, final String keywords, final Set<CategoryEntity> categories) {
        this.searchVariantDao.update(searchVariantEntity, keywords, categories);
    }
}
