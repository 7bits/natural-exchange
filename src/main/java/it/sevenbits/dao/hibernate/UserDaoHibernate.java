package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;

import java.util.List;

/**
 * Тестовая имплементация интерфейса UserDao
 */

public class UserDaoHibernate implements UserDao {

    public void create(User user) {
    }

    @Override
    public User findById(final Integer id) {
        User user = new User();
        //user.setId(id);
        user.setFirstName("Dmitry " + id);
        return user;
    }

    @Override
    public List<User> find() {
        List<User> userList = null;
        User user = new User();

        //user.setId(1);
        user.setFirstName("Dmitry ");
        userList.add(user);

        //user.setId(2);
        user.setFirstName("Annie");
        userList.add(user);


        //user.setId(3);
        user.setFirstName("Valentine");
        userList.add(user);

        return userList;
    }

    public void update(User user) {
    }

    public void delete(User user) {
    }

}
