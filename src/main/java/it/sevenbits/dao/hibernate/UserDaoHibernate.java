package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Тестовая имплементация интерфейса UserDao
 */

@Repository(value = "userDao")
public class UserDaoHibernate implements UserDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public UserDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public void create(User user) {
        UserEntity tmp = new UserEntity(user);
        this.hibernateTemplate.save(tmp);
    }

    @Override
    public Boolean isExistUserWithEmail(String email) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("email",email));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        if(!users.isEmpty())
            return true;
        return false;
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
        List<User> userList = new ArrayList<User>();
        User user = new User();

        //user.setId(1);
        user.setFirstName("Dmitry ");
        userList.add(user);

        //user.setId(2);
        user=new User();
        user.setFirstName("Annie");
        userList.add(user);

        user=new User();
        //user.setId(3);
        user.setFirstName("Valentine");
        userList.add(user);

        return userList;
    }


    public  User findUserByEmail(String email) throws UsernameNotFoundException {

        if (!email.equals("123pass@login.ru")) {
            throw new UsernameNotFoundException(email + " not found");
        }

        User user = new User();
        user.setFirstName("bob");
        user.setLastName("Endy");
        user.setPassword("123pass");
        user.setEmail("123pass@local.ru");


        return user;
    }


    public void update(User user) {
    }

    public void delete(User user) {
    }

}
