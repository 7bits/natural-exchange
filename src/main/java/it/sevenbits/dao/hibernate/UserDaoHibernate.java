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

    private UserEntity toEntity(final User user) {
        UserEntity userEntity = new UserEntity(
                user.getFirstName(), user.getEmail(), user.getLastName(), user.getVk_link(), user.getCreatedDate(),
                user.getUpdateDate(), user.getIsDeleted(), user.getPassword(), user.getRole(), user.getActivationCode(),
                user.getActivationDate());
        return userEntity;
    }

    public void create(final User user) {
        UserEntity userEntity = toEntity(user);
        this.hibernateTemplate.save(userEntity);
    }

    @Override
    public Boolean isExistUserWithEmail(final String email) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("email", email));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        return !users.isEmpty();
    }

    @Override
    public User findById(final Integer id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("id", id));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        return users.get(0);
    }

    @Override
    public UserEntity findEntityByEmail(final String name) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("email", name));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        return users.get(0);
    }

    @Override
    public UserEntity findEntityByVkId(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("vk_link", id));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        return users.get(0);
    }

    @Override
    public List<User> find() {
        List<User> userList = new ArrayList<User>();
        return userList;
    }


    public  User findUserByEmail(final String email) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("email", email));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        if (users.size() != 1) {
            throw new UsernameNotFoundException(email + " not found.");

        }
        return users.get(0);
    }


    @Override
    public void updateActivationCode(User user) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("email", user.getEmail()));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        if (users.size() != 1) {
            throw new UsernameNotFoundException(user.getEmail() + " not found.");
        }
        users.get(0).setActivationDate(0L);
        users.get(0).setActivationCode(null);
        this.hibernateTemplate.update(users.get(0));
    }

    @Override
    public void update(final User user) {
        this.hibernateTemplate.update(toEntity(user));
    }

    @Override
    public void delete(final User user) {
        this.hibernateTemplate.delete(toEntity(user));
    }

}
