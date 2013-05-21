package it.sevenbits.dao.impl;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.hibernate.Advertisement;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Тестовая имплементация интерфейса UserDao
 */
@Repository(value = "advertisementDao")
public class AdvertisementDaoImpl implements AdvertisementDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public AdvertisementDaoImpl(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public void create(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return;
    }

    /**
    * Creates and returns the ad. Works with no database.
    */
    @Override
    public Advertisement findById(final Long id) {
        Advertisement advertisement = this.hibernateTemplate.get(Advertisement.class, id);

        return  advertisement;
    }

    @Override
    public List<Advertisement> findAll() {
        List<Advertisement> advertisementList = new ArrayList<Advertisement>();
        advertisementList = this.hibernateTemplate.loadAll(Advertisement.class);

        return advertisementList;
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
}
