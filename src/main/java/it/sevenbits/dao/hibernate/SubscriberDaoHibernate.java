package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.hibernate.SubscriberEntity;
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
@Repository(value = "subscriberDao")
public class SubscriberDaoHibernate implements SubscriberDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public SubscriberDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public void create(final Subscriber subscriber) {
        SubscriberEntity tmp = new SubscriberEntity(subscriber.getEmail());
        this.hibernateTemplate.save(tmp);
    }

    @Override
    public Subscriber findById(final Integer id) {
        return new Subscriber();
    }

    @Override
    public boolean isExists(final Subscriber subscriber) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SubscriberEntity.class);
        criteria.add(Restrictions.eq("email", subscriber.getEmail()));
        List<SubscriberEntity> subscribers = this.hibernateTemplate.findByCriteria(criteria);
        if (subscribers.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<Subscriber> find() {
        DetachedCriteria criteria = DetachedCriteria.forClass(SubscriberEntity.class);
        return convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }

    public void update(final Subscriber subscriber) {
    }

    public void delete(final Subscriber subscriber) {
    }

    private List<Subscriber> convertEntityList(final List entities) {
        List<Subscriber> subscribers = new ArrayList<Subscriber>();
        if (entities != null) {
            List<SubscriberEntity> advertisementEntityList = (List<SubscriberEntity>) entities;
            for (SubscriberEntity entity : advertisementEntityList) {
                subscribers.add(entity);
            }
        }
        return subscribers;
    }

}
