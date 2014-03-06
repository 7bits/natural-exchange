package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.hibernate.SearchVariantEntity;
import it.sevenbits.util.TimeManager;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(SearchVariantDaoHibernate.class);
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

    @Override
    /**
     * Updates entity fields to new values if there is not exist another
     * entity with new-values. If there is - do nothing.--
     */
    public void updateAdvertSearch(final String email, final String oldKeyWordsParam, final String oldCategoriesParam,
                                   final String keyWords, final String categories
    ){

        logger.debug("oldValues"+oldKeyWordsParam+" "+oldCategoriesParam);
        logger.debug("newValues"+keyWords+" "+categories);

        DetachedCriteria criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
        criteria.add(Restrictions.like("email", email));
        criteria.add(Restrictions.like("keyWords", oldKeyWordsParam));
        criteria.add(Restrictions.like("categories", oldCategoriesParam));

        List<SearchVariantEntity> searchVariantEntities = this.hibernateTemplate.findByCriteria(criteria);
        SearchVariantEntity entity = searchVariantEntities.get(0);
        entity.setCategories(categories);
        entity.setKeyWords(keyWords);
        entity.setCreatedDate(TimeManager.getTime());
        this.hibernateTemplate.update(entity);
    }

    public boolean isExist(final SearchVariant searchVariant) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
        criteria.add(Restrictions.like("email", searchVariant.getEmail()));
        criteria.add(Restrictions.like("keyWords", searchVariant.getKeyWords()));
        criteria.add(Restrictions.like("categories", searchVariant.getCategories()));

        List<SearchVariantEntity> searchVariantEntities = this.hibernateTemplate.findByCriteria(criteria);
        System.out.println("searchVatEntities is empty =  " + searchVariantEntities.isEmpty() + " = !res");
        return (!searchVariantEntities.isEmpty());
    }

}

    /*
        DetachedCriteria criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
        criteria.add(Restrictions.like("email", email));
        criteria.add(Restrictions.like("keyWords", keyWords));
        criteria.add(Restrictions.like("categories", categories));
        List<SearchVariantEntity> variants = this.hibernateTemplate.findByCriteria(criteria);
        if (variants != null) {
            return;
        }

        this.hibernateTemplate.clear(); //?
        criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
        criteria.add(Restrictions.like("email", email));
        criteria.add(Restrictions.like("keyWords", oldKeyWordsParam));
        criteria.add(Restrictions.like("categories", oldCategoriesParam));

        List<SearchVariantEntity> searchVariantEntities = this.hibernateTemplate.findByCriteria(criteria);
        SearchVariantEntity entity = searchVariantEntities.get(0);
        entity.setCategories(categories);
        entity.setKeyWords(keyWords);
        entity.setCreatedDate(TimeManager.getTime());
        this.hibernateTemplate.saveOrUpdate(entity);*/
