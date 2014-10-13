/**
 *  Class, which implements AdvertisementDao for Hibernate
 */
package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.CategoryDao;
import it.sevenbits.dao.TagDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Tag;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.CategoryEntity;
import it.sevenbits.entity.hibernate.TagEntity;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.TimeManager;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.*;

/**
 * Class, which implements AdvertisementDao for Hibernate
 */
@Repository(value = "advertisementDao")
public class AdvertisementDaoHibernate implements AdvertisementDao {

    private HibernateTemplate hibernateTemplate;

    private final Logger logger = LoggerFactory.getLogger(AdvertisementDaoHibernate.class);

    @Autowired
    public AdvertisementDaoHibernate(@Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Resource(name = "categoryDao")
    private CategoryDao categoryDao;

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "tagDao")
    private TagDao tagDao;

    @Resource(name = "mailService")
    private MailSenderService mailSenderService;

    private AdvertisementEntity toEntity(final Advertisement advertisement) {
        AdvertisementEntity tmp = new AdvertisementEntity();
        tmp.setTitle(advertisement.getTitle());
        tmp.setCreatedDate(advertisement.getCreatedDate());
        tmp.setPhotoFile(advertisement.getPhotoFile());
        tmp.setText(advertisement.getText());
        tmp.setIs_deleted(advertisement.getIs_deleted());
        tmp.setUpdatedDate(advertisement.getUpdatedDate());
        return tmp;
    }

    @Override
    public Advertisement create(final Advertisement advertisement, final String categoryName, final String userName, final Set<Tag> tags) {
        AdvertisementEntity advertisementEntity = toEntity(advertisement);
        CategoryEntity categoryEntity = this.categoryDao.findEntityBySlug(categoryName);
        UserEntity userEntity = this.userDao.findEntityByEmail(userName);
        advertisementEntity.setCategoryEntity(categoryEntity);
        advertisementEntity.setUserEntity(userEntity);
        this.translateIntoTagEntityAndUpdateAdvertisement(tags, advertisementEntity);
        this.hibernateTemplate.save(advertisementEntity);
        try {
            mailSenderService.sendNotifyToModerator(advertisementEntity.getId(), advertisementEntity.getCategory().getSlug());
        } catch (MailException ex) {
//            TODO нужно обработать это исключение
            logger.warn("Notification couldn't been sent");
        }
        return  advertisementEntity;
    }

    private void translateIntoTagEntityAndUpdateAdvertisement(Set<Tag> tags, AdvertisementEntity advertisementEntity) {
        if (tags != null) {
            if (!tags.isEmpty()) {
                Set<TagEntity> newTags = new HashSet<TagEntity>();
                for (Tag currentTag : tags) {
                    TagEntity addingTag = new TagEntity();
                    addingTag.setName(currentTag.getName());
                    addingTag.setAdvertisement(advertisementEntity);
                    newTags.add(addingTag);
                }
                Set<TagEntity> oldTags = advertisementEntity.getTags();
                if (oldTags != null) {
                    for (TagEntity tagToDel : oldTags) {
                        hibernateTemplate.delete(tagToDel);
                    }
                }
                advertisementEntity.setTags(newTags);
            } else {
                this.tagDao.deleteTagsByAdvertisementId(advertisementEntity.getId());
                advertisementEntity.setTags(null);
            }
        }
    }


    /**
    * Creates and returns the ad. Works with no database.
    */
    @Override
    public Advertisement findById(final Long id) {
        return this.hibernateTemplate.get(AdvertisementEntity.class, id);
    }

    @Override
    public void update(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.

    }

    @Override
    public void delete(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
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

    @Override
    public void changeDeleted(Long id) {
        AdvertisementEntity advertisementEntity = this.hibernateTemplate.get(AdvertisementEntity.class, id);
        boolean deleteState = advertisementEntity.getIs_deleted();
        advertisementEntity.setIs_deleted(!deleteState);
        hibernateTemplate.update(advertisementEntity);
    }


    @Override
    public void update(Long id, Advertisement advertisement, String categoryName, Set<Tag> tags) {
        AdvertisementEntity advertisementEntity = this.hibernateTemplate.get(AdvertisementEntity.class, id);
        advertisementEntity.setTitle(advertisement.getTitle());
        advertisementEntity.setPhotoFile(advertisement.getPhotoFile());
        advertisementEntity.setText(advertisement.getText());
        advertisementEntity.setIs_deleted(advertisement.getIs_deleted());
        advertisementEntity.setUpdatedDate(TimeManager.getTime());
        advertisementEntity.setCategoryEntity(this.categoryDao.findEntityBySlug(categoryName));

        this.translateIntoTagEntityAndUpdateAdvertisement(tags, advertisementEntity);
        hibernateTemplate.update(advertisementEntity);
    }

    @Override
    public List<Advertisement> findUserAdvertisements(User user) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(AdvertisementEntity.class, "advertisement");
        UserEntity userEntity = this.userDao.findEntityByEmail(user.getEmail());
        criteria.add(Restrictions.eq("userEntity", userEntity));
        criteria.add(Restrictions.eq("advertisement.is_deleted", Boolean.FALSE));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return this.convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }

    @Override
    public List<Advertisement> findAdvertisementsWithCategoryAndKeyWords(
            String[] categories,
            String[] keyWords,
            SortOrder sortOrder,
            String sortPropertyName
    ) {
        return this.findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(
                categories, keyWords, false, sortOrder, sortPropertyName, null, null);
    }

    @Override
    public List<Advertisement> findAdvertisementsWithKeyWordsFilteredByDelete(
        String[] keyWords,
        SortOrder sortOrder,
        String sortPropertyName,
        Boolean isDeleted,
        Long dateFrom,
        Long dateTo) {
        return this.findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(
            null, keyWords, isDeleted, sortOrder, sortPropertyName, dateFrom, dateTo
        );
    }

    @Override
    public List<Advertisement> findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(
        String[] categories,
        String[] keyWords,
        Boolean isDeleted,
        SortOrder sortOrder,
        String sortPropertyName,
        Long dateFrom,
        Long dateTo) {
        String sortByName = (sortPropertyName == null)
                ? Advertisement.CREATED_DATE_COLUMN_CODE
                : (Advertisement.TITLE_COLUMN_CODE.equals(sortPropertyName) ? sortPropertyName : Advertisement.CREATED_DATE_COLUMN_CODE)
                ;
        DetachedCriteria criteria = DetachedCriteria.forClass(AdvertisementEntity.class, "advertisement");
        criteria.createAlias("tags", "tag", Criteria.LEFT_JOIN);
        switch (sortOrder) {
            case ASCENDING :
                criteria.addOrder(Order.asc(sortByName));
                break;
            case DESCENDING :
                criteria.addOrder(Order.desc(sortByName));
                break;
            default:
                break;
        }

        if (categories != null) {
            criteria.createAlias("categoryEntity", "category");
            Disjunction disjunction = Restrictions.disjunction();
            for (int i = 0; i < categories.length; i++) {
                disjunction.add(Restrictions.eq("category.slug", categories[i]));
            }
            criteria.add(disjunction);
        }

        if (keyWords != null) {
            Disjunction disjunction = Restrictions.disjunction();
            for (int i = 0; i < keyWords.length; i++) {
                disjunction.add(Restrictions.like("advertisement.title", "%" + keyWords[i] + "%") );
                disjunction.add(Restrictions.like("tag.name","%" + keyWords[i] + "%"));
                criteria.add(disjunction);
                disjunction = Restrictions.disjunction();
            }
            criteria.add(disjunction);
        }

        if (dateFrom != null || dateTo != null) {
            Criterion dateCriterion = null;
            if (dateFrom != null && dateTo != null) {
                dateCriterion = Restrictions.between("createdDate", dateFrom, dateTo);
            }
            if (dateFrom == null) {
                dateCriterion = Restrictions.le("createdDate", dateTo);
            }
            if (dateTo == null) {
                dateCriterion = Restrictions.ge("createdDate", dateFrom);
            }
            criteria.add(dateCriterion);
        }
        if (isDeleted == null) {
            isDeleted = false;
        }
        criteria.add(Restrictions.eq("is_deleted", isDeleted));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return this.convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }
}
