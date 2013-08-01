package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.hibernate.SubscriberEntity;
import org.hibernate.SessionFactory;
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

    public void create(Subscriber subscriber) {
        SubscriberEntity tmp = new SubscriberEntity("dimaaasik.s@gmail.com");
        this.hibernateTemplate.save(tmp);
    }

    @Override
    public Subscriber findById(final Integer id) {
        return new Subscriber();
    }

    @Override
    public List<Subscriber> find() {
        List<Subscriber> subscriberList = new ArrayList<Subscriber>();


        return subscriberList;
    }

    public void update(Subscriber subscriber) {
    }

    public void delete(Subscriber subscriber) {
    }

}
