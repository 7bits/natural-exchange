package it.sevenbits.dao;

import it.sevenbits.entity.hibernate.Advertisement;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью Advertisement
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
    Advertisement findById(Integer id);

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
