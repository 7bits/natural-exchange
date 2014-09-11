package it.sevenbits.dao;

import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.util.SortOrder;

import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью UserEntity
 */
public interface UserDao {

    /**
     * добавить пользователя
     * @param user to create entity
     */
    void create(User user);

    /**
     *  найти пользователя по id
     * @param id  of entity
     * @return userEntity
     */
    User findById(Long id);

    UserEntity findEntityByEmail(String name);

    /**
     *
     * @param id  of UserEntity
     * @return  User - if user was found
     *          null - if no users with id like that
     */
    UserEntity findEntityByVkId(String id);

    /**
     * возвращает  список всех пользователей
     * @return   list of all
     */
    List<User> find();

    List<User> findAllBannedUsers();

    List<User> findAllNotBannedUsers();

    List<User> findUsersByKeywordsDateAndBan(final String keyWords,
                                             final Long dateFrom,
                                             final Long dateTo,
                                             final boolean isBanned,
                                             SortOrder sortOrder);

    void changeBan(Long id);

    Boolean isExistUserWithEmail(String email);
    /**
     * изменить пользователя
     * @param user   to change
     */
    void update(User user);
    /**
     * update user after registration
     * @param  user - the user with the confirmed registration
     */
    void updateActivationCode(User user) ;

    /**
     *
     * @param email e-mail
     * @return  user with given e-mail
     */
    User findUserByEmail(final String email);

    /**
     * удалить пользователя
     * @param user to delete
     */
    void delete(User user);

    List<User> findAllModerators();

    void updateData(User user);

    void setBanned(String userEmail);
}
