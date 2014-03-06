package it.sevenbits.dao;

import it.sevenbits.entity.Subscriber;
import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью UserEntity
 */
public interface SubscriberDao {

    /**
     * добавить подписчика
     * @param subscriber - subscriber to add in DB
     */
    void create(Subscriber subscriber);

    /**
     *
     */
    void update(final String oldEmail, final String newEmail);

    /**
     *  найти подписчика по id
     * @param id  identif. number of subscriber in DB
     * @return    subscriber
     */
    Subscriber findById(Integer id);

    boolean isExists(Subscriber subscriber);

    /**
     * возвращает  список всех подписчиков
     * @return  list of all subscribers
     */
    List<Subscriber> find();


    /**
     * изменить подписчиков
     * @param subscriber subscriber to change
    void update(Subscriber subscriber);

    /**
     * удалить подписчиков
     * @param subscriber subscriber to delete
     */
    void delete(Subscriber subscriber);
}
