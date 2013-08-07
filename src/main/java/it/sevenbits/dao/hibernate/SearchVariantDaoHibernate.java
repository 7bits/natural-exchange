package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.hibernate.SearchVariantEntity;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
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

//    private SessionFactory sessionFactory;
//
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public SearchVariantDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }


    public void create(SearchVariant searchVariant) {
        SearchVariantEntity tmp = new SearchVariantEntity(searchVariant.getEmail(),searchVariant.getKeyWords(),searchVariant.getCategories());
        this.hibernateTemplate.save(tmp);
    }

    @Override
    public SearchVariant findById(final Long id) {
        SearchVariantEntity tmp =  this.hibernateTemplate.get(SearchVariantEntity.class,id);
        return  tmp;
    }

    @Override
    public List<SearchVariant> find() {
        DetachedCriteria criteria = DetachedCriteria.forClass(SearchVariantEntity.class);
        List<SearchVariant> searchVariants = convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
        return searchVariants;
    }

    public void update(SearchVariant searchVariant) {
    }

    public void delete(SearchVariant searchVariant) {
        SearchVariantEntity tmp = new SearchVariantEntity(searchVariant.getEmail(),searchVariant.getKeyWords(),searchVariant.getCategories());
        this.hibernateTemplate.delete(tmp);
    }

    private List<SearchVariant> convertEntityList(List entities) {
        List<SearchVariant> sList = new ArrayList<SearchVariant>();
        if (entities != null) {
            List<SearchVariantEntity> list = (List<SearchVariantEntity>)entities;
            for (SearchVariantEntity entity : list) {
                sList.add(entity);
            }
        }
        return sList;
    }
}
