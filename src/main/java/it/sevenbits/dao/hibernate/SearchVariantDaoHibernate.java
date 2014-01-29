package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.hibernate.SearchVariantEntity;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Тестовая имплементация интерфейса UserDao
 */
@Repository(value = "searchVariantDao")
public class SearchVariantDaoHibernate implements SearchVariantDao {
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public SearchVariantDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }


    public void create(final SearchVariant searchVariant) {
        SearchVariantEntity tmp = new SearchVariantEntity(searchVariant.getEmail(), searchVariant.getKeyWords(),
                searchVariant.getCategories());
        this.hibernateTemplate.save(tmp);
    }

    @Override
    public SearchVariant findById(final Long id) {
        return this.hibernateTemplate.get(SearchVariantEntity.class, id);
    }

    @Override
    public List<SearchVariant> find() {
        DetachedCriteria criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
       return convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }

    public void update(final SearchVariant searchVariant, final String keyWordsParam,
                       final String categoriesParam
    ) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
        criteria.add(Restrictions.eq("email", searchVariant.getEmail()));
        criteria.add(Restrictions.eq("keyWords", searchVariant.getKeyWords()));
        criteria.add(Restrictions.eq("categories", searchVariant.getCategories()));
        List<SearchVariantEntity> entities = this.hibernateTemplate.findByCriteria(criteria);
        SearchVariantEntity searchVar = entities.get(0);
        searchVar.setCategories(categoriesParam);
        searchVar.setKeyWords(keyWordsParam);
        this.hibernateTemplate.update(searchVar);
    }

    public void delete(final SearchVariant searchVariant) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
        criteria.add(Restrictions.eq("email", searchVariant.getEmail()));
        criteria.add(Restrictions.eq("keyWords", searchVariant.getKeyWords()));
        criteria.add(Restrictions.eq("categories", searchVariant.getCategories()));
        List<SearchVariantEntity> entities = this.hibernateTemplate.findByCriteria(criteria);
        for (SearchVariantEntity tmp: entities) {
            this.hibernateTemplate.delete(tmp);
        }
    }

    @Override
    public List<SearchVariant> findByEmail(final String email) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
        criteria.add(Restrictions.eq("email", email));
        return convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }

    private List<SearchVariant> convertEntityList(final List entities) {
        List<SearchVariant> sList = new ArrayList<SearchVariant>();
        if (entities != null) {
            List<SearchVariantEntity> list = (List<SearchVariantEntity>) entities;
            for (SearchVariantEntity entity : list) {
                sList.add(entity);
            }
        }
        return sList;
    }


}
