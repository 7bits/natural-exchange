/**
 *  Class, which implements AdvertisementDao for Hibernate
 */
package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.CategoryDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.CategoryEntity;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.SortOrder;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


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

    @Resource(name = "categoryDao")
    private CategoryDao categoryDao;

    private AdvertisementEntity toEntity(final Advertisement advertisement) {
        AdvertisementEntity tmp = new AdvertisementEntity();
        tmp.setTitle(advertisement.getTitle());
        tmp.setCreatedDate(advertisement.getCreatedDate());
        tmp.setPhotoFile(advertisement.getPhotoFile());
        tmp.setText(advertisement.getText());
        tmp.setIsDeleted(advertisement.getIsDeleted());
        tmp.setUpdatedDate(advertisement.getUpdatedDate());
        return tmp;
    }

    @Override
    public Advertisement create(final Advertisement advertisement, final String categoryName, final User user) {
        AdvertisementEntity advertisementEntity = toEntity(advertisement);
        CategoryEntity categoryEntity = this.categoryDao.findEntityByName(categoryName);
        advertisementEntity.setCategoryEntity(categoryEntity);
        return this.hibernateTemplate.merge(advertisementEntity);
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
            default:
                //
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
    public List<Advertisement> findAllAdvertisementsWithCategoryAndOrderBy(
            final String category, final SortOrder sortOrder, final String sortPropertyName
    ) {
        String [] args = new String[1];
        Object [] values = new Object[1];
        args[0] = "categoryParam";
        values[0] = category;
        String sortByName = (sortPropertyName == null)
                ? Advertisement.CREATED_DATE_COLUMN_CODE
                : (Advertisement.TITLE_COLUMN_CODE.equals(sortPropertyName) ? sortPropertyName : Advertisement.CREATED_DATE_COLUMN_CODE)
                ;
        List<AdvertisementEntity> lst = null;
        String sortTypeForNamedQueryName = null;
        String sortByTargetForNamedQueryName = null;
        switch (sortOrder) {
            case ASCENDING :
                sortTypeForNamedQueryName = "Asc";
                break;
            case DESCENDING :
                sortTypeForNamedQueryName = "Desc";
                break;
            default:
                //
                break;
        }
        if (sortByName.equals(Advertisement.TITLE_COLUMN_CODE)) {
            sortByTargetForNamedQueryName = "Title";
        } else {
            sortByTargetForNamedQueryName = "Date";
        }
        String namedQueryName = "findAllAdvertisementsWithCategoryAndOrderBy" +
                sortByTargetForNamedQueryName +
                sortTypeForNamedQueryName;
        lst = this.hibernateTemplate.findByNamedQueryAndNamedParam(namedQueryName, args, values);
        return convertEntityList(lst);
    }

//    @Override
//    public List<Advertisement> findAll2() {
//        DetachedCriteria criteria = DetachedCriteria.forClass(AdvertisementEntity.class)
//        .createAlias("categoryEntity","category")
//        .add( Restrictions.eq("category.name", "notclothes") );
//        //criteria.createAlias()
//        return this.convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
//    }


    /**
     * Search advertisements from DB, which match category and key words
     * @param categories if null, it isn't use in selection from DB
     * @param keyWords key words,which searching in title
     * @return
     */
    @Resource(name = "mailService")
    private MailSenderService mailSenderService;

    @Override
    public List<Advertisement> findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(final String[] categories,
                                                                                   final String[] keyWords,
                                                                                   final SortOrder sortOrder,
                                                                                   final String sortPropertyName) {
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
            default:
                //
                break;
        }
        if (categories != null) {
            criteria.createAlias("categoryEntity", "category");
            Disjunction disjunction = Restrictions.disjunction();
            for (int i = 0; i < categories.length; i++) {
                disjunction.add(Restrictions.eq("category.name", categories[i]));
            }
            criteria.add(disjunction);
        }
        if (keyWords != null) {
            for (int i = 0; i < keyWords.length; i++) {
                criteria.add(Restrictions.like("title", "%" + keyWords[i] + "%"));
            }
        }
        return this.convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<Advertisement> convertEntityList(final List entities) {
        List<Advertisement> advertisementList = new ArrayList<Advertisement>();
        if (entities != null) {
            List<AdvertisementEntity> advertisementEntityList = (List<AdvertisementEntity>) entities;
            for (AdvertisementEntity entity : advertisementEntityList) {
                advertisementList.add(entity);
            }
        }
        return advertisementList;
    }
}
