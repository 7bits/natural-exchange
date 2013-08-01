package it.sevenbits.dao;

import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.User;

import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью UserEntity
 */
public interface SubscriberDao {

    /**
     * добавить подписчика
     * @param subscriber
     */
    void create(Subscriber subscriber);

    /**
     *  найти подписчика по id
     * @param id
     * @return
     */
    Subscriber findById(Integer id);

    /**
     * возвращает  список всех подписчиков
     * @return
     */
    List<Subscriber> find();

    /**
     * изменить подписчиков
     * @param subscriber
    void update(Subscriber subscriber);

    /**
     * удалить подписчиков
     * @param subscriber
     */
    void delete(Subscriber subscriber);
}
