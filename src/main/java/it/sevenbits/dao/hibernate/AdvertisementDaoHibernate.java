package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.util.SortOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.sevenbits.util.SortOrder.ASCENDING;

/**
 * Class, which implements AdvertisementDao for Hibernate
 */
@Repository(value = "advertisementDao")
public class AdvertisementDaoHibernate implements AdvertisementDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public AdvertisementDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public Advertisement create(final Advertisement advertisement) {
        //TODO Check encoding trouble with MySQL
        return this.hibernateTemplate.merge(advertisement);
    }

    /**
    * Creates and returns the ad. Works with no database.
    */
    @Override
    public Advertisement findById(final Long id) {
        AdvertisementEntity advertisementEntity = this.hibernateTemplate.get(AdvertisementEntity.class, id);

        return  advertisementEntity;
    }

    @Override
    public List<Advertisement> findAll() {
        return this.findAll(ASCENDING, Advertisement.CREATED_DATE_COLUMN_CODE);
    }

    @SuppressWarnings("incomplete-switch")
	@Override
	public List<Advertisement> findAll(final SortOrder sortOrder, final String sortPropertyName) {

        //TODO: Move default sort column to properties
        String sortByName = (sortPropertyName == null)
                ? Advertisement.CREATED_DATE_COLUMN_CODE
                : (Advertisement.TITLE_COLUMN_CODE.equals(sortPropertyName) ? sortPropertyName : Advertisement.CREATED_DATE_COLUMN_CODE)
        ;
        DetachedCriteria criteria = DetachedCriteria.forClass(AdvertisementEntity.class);
        switch (sortOrder) {
            case ASCENDING :
                criteria.addOrder(Order.asc(sortByName));
                break;
            case DESCENDING :
                criteria.addOrder(Order.desc(sortByName));
                break;
        }

        return this.convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }

    @Override
    public void update(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return;
    }

    @Override
    public void delete(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return;
    }

    @Override
    public List<Advertisement> findAllAdvertisementsWithCategoryAndOrderBy(String category,
                                                                           final SortOrder sortOrder,
                                                                           final String sortPropertyName) {
        String args[] = new String[1];
        Object values[] = new Object[1];
        args[0] = "categoryParam";
        values[0] = category;
//        List<AdvertisementEntity> lst = this.hibernateTemplate.findByNamedQueryAndNamedParam(
//                            "findAllAdvertisementsWithCategoryAndOrderByTitleAsc",
//                            args,
//                            values);
        String sortByName = (sortPropertyName == null)
                ? Advertisement.CREATED_DATE_COLUMN_CODE
                : (Advertisement.TITLE_COLUMN_CODE.equals(sortPropertyName) ? sortPropertyName : Advertisement.CREATED_DATE_COLUMN_CODE)
                ;
        List<AdvertisementEntity> lst = null;
        if(sortByName.equals(Advertisement.TITLE_COLUMN_CODE))
            switch (sortOrder) {
                case ASCENDING :
                    lst =  this.hibernateTemplate.findByNamedQueryAndNamedParam(
                            "findAllAdvertisementsWithCategoryAndOrderByTitleAsc",
                            args,
                            values);
                    break;
                case DESCENDING :
                    lst =  this.hibernateTemplate.findByNamedQueryAndNamedParam(
                            "findAllAdvertisementsWithCategoryAndOrderByTitleDesc",
                            args,
                            values);
                    break;
            }
        else
            switch (sortOrder) {
                case ASCENDING :
                    lst =  this.hibernateTemplate.findByNamedQueryAndNamedParam(
                            "findAllAdvertisementsWithCategoryAndOrderByDateAsc",
                            args,
                            values);
                    break;
                case DESCENDING :
                    lst =  this.hibernateTemplate.findByNamedQueryAndNamedParam(
                            "findAllAdvertisementsWithCategoryAndOrderByDateDesc",
                            args,
                            values);
                    break;
        }
        return convertEntityList(lst);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Advertisement> convertEntityList(List entities) {

        List<Advertisement> advertisementList = new ArrayList<Advertisement>();
        if (entities != null) {
            List<AdvertisementEntity> advertisementEntityList = (List<AdvertisementEntity>)entities;
            for (AdvertisementEntity entity : advertisementEntityList) {
                advertisementList.add(entity);
            }
        }

        return advertisementList;
    }
}
