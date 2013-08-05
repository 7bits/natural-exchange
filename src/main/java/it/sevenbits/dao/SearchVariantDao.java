package it.sevenbits.dao;

import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.Subscriber;

import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью UserEntity
 */
public interface SearchVariantDao {

    /**
     * добавить подписчика
     * @param searchVariant
     */
    void create(SearchVariant searchVariant);

    /**
     *  найти подписчика по id
     * @param id
     * @return
     */
    SearchVariant findById(Integer id);

    /**
     * возвращает  список всех подписчиков
     * @return
     */
    List<SearchVariant> find();

    /**
     * изменить подписчиков
     * @param searchVariant
    void update(Subscriber subscriber);

    /**
     * удалить подписчиков
     * @param searchVariant
     */
    void delete(SearchVariant searchVariant);
}
