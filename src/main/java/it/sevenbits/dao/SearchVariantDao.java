package it.sevenbits.dao;

import it.sevenbits.entity.SearchVariant;

import java.util.List;

/**
 *Интерфейс
 */
public interface SearchVariantDao {

    /**
     * create variant of search and add it to DB
     * @param searchVariant  search variant
     */
    void create(SearchVariant searchVariant);

    /**
     *  id
     * @param id -
     * @return
     */
    SearchVariant findById(Long id);

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
