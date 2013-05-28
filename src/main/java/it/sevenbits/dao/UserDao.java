package it.sevenbits.dao;

import it.sevenbits.entity.User;

import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью User 
 */
public interface UserDao {

    /**
     * добавить пользователя
     * @param user
     */
    void create(User user);

    /**
     *  найти пользователя по id
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * возвращает  список всех пользователей
     * @return
     */
    List<User> find();

    /**
     * изменить пользователя
     * @param user
     */
    void update(User user);

    /**
     * удалить пользователя
     * @param user
     */
    void delete(User user);
}