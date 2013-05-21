package it.sevenbits.dao;

import it.sevenbits.entity.Advertisement;

import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью AdvertisementEntity
 */
public interface AdvertisementDao {

    /**
     * Создает новое объявление
     * @param advertisement
     */
    void create(Advertisement advertisement);

    /**
     * Поиск объявления по  id
     * @param id
     * @return
     */
    Advertisement findById(Long id);

    /**
     * возвращает все объявления
     * @return
     */
    List<Advertisement> findAll();

    /**
     * изменить объявление
     * @param advertisement
     */
    void update(Advertisement advertisement);

    /**
     * удалить объявление
     * @param advertisement
     */
    void delete(Advertisement advertisement);
}
