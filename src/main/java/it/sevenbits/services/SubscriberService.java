package it.sevenbits.services;

import it.sevenbits.repository.dao.SubscriberDao;
import it.sevenbits.repository.entity.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberDao subscriberDao;

    public Boolean isExists(final Subscriber subscriber) {
        return this.subscriberDao.isExists(subscriber);
    }

    public void create(final Subscriber subscriber) {
        this.subscriberDao.create(subscriber);
    }
}
