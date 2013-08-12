package it.sevenbits.dao;

import it.sevenbits.entity.User;

import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью UserEntity
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

    Boolean isExistUserWithEmail(String email);
    /**
     * изменить пользователя
     * @param user
     */
    void update(User user);

    /**
     *
     * @param email e-mail
     * @return  user with given e-mail
     */
    public  User findUserByEmail(final String email);

    /**
     * удалить пользователя
     * @param user
     */
    void delete(User user);
}
