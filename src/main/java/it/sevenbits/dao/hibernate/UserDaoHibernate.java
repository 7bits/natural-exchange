package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.TimeManager;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Тестовая имплементация интерфейса UserDao
 */

@Repository(value = "userDao")
public class UserDaoHibernate implements UserDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public UserDaoHibernate(@Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    private UserEntity toEntity(final User user) {
        return new UserEntity(
                user.getFirstName(), user.getEmail(), user.getLastName(), user.getVk_link(), user.getCreatedDate(),
                user.getUpdateDate(), user.getIsBanned(), user.getPassword(), user.getRole(), user.getActivationCode(),
                user.getActivationDate(), user.getAvatar());
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
    public User findById(final Long id) {
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
        UserEntity result = null;
        if(!users.isEmpty()) {
            result = users.get(0);
        }
        return result;
    }

    @Override
    public UserEntity findEntityByVkId(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("vk_link", id));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        UserEntity result = null;
        if(!users.isEmpty()) {
            result = users.get(0);
        }
        return result;
    }

    @Override
    public List<User> find() {
        return new ArrayList<User>();
    }

    @Override
    public List<User> findAllBannedUsers() {
        return null;
    }

    @Override
    public List<User> findAllNotBannedUsers() {
        return null;
    }

    @Override
    public List<User> findUsersByKeywordsDateAndBanOrderBy(String keyWords,
                                                           Long dateFrom,
                                                           Long dateTo,
                                                           boolean isBanned,
                                                           SortOrder sortOrder) {
        String sortByName = "createdDate";
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        switch (sortOrder) {
            case ASCENDING :
                criteria.addOrder(Order.asc(sortByName));
                break;
            case DESCENDING :
                criteria.addOrder(Order.desc(sortByName));
                break;
            default:
                //
                break;
        }

        //по чему искать?
        if (keyWords != null) {
            String[] keyWordsForSearch = stringToTokensArray(keyWords);
            Disjunction disjunction = Restrictions.disjunction();
            for (int i = 0; i < keyWordsForSearch.length; i++) {
                disjunction.add(Restrictions.like("firstName", "%" + keyWordsForSearch[i] + "%") );
                disjunction.add(Restrictions.like("lastName","%" + keyWordsForSearch[i] + "%"));
                disjunction.add(Restrictions.like("email","%" + keyWordsForSearch[i] + "%"));
                criteria.add(disjunction);
                disjunction = Restrictions.disjunction();
            }
        }

        if (dateFrom != null || dateTo != null) {
            Criterion dateCriterion = null;
            if (dateFrom != null && dateTo != null) {
                dateCriterion = Restrictions.between("createdDate", dateFrom, dateTo);
            }
            if (dateFrom == null) {
                //less or equal
                dateCriterion = Restrictions.le("createdDate", dateTo);
            }
            if (dateTo == null) {
                //greater or equal
                dateCriterion = Restrictions.ge("createdDate", dateFrom);
            }
            criteria.add(dateCriterion);
        }
        criteria.add(Restrictions.eq("isBanned", isBanned));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<User> simpleSearch = this.convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
        return this.convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }

    private List<User> convertEntityList(List entities) {
        List<User> userList = new ArrayList<User>();
        if (entities != null) {
            List<UserEntity> userEntityList = (List<UserEntity>) entities;
            for (UserEntity entity : userEntityList) {
                userList.add(entity);
            }
        }
        return userList;
    }

    private String[] stringToTokensArray(final String str) {
        if (str == null) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(str);
        String[] words = new String[token.countTokens()];
        for (int i = 0 ; i < words.length ; i++) {
            words[i] = token.nextToken();
        }
        return words;
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

    @Override
    public List<User> findAllModerators() {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("role", "ROLE_MODERATOR"));
        return this.hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    public void updateData(User userNewParam) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String oldEmail = auth.getName();
        DetachedCriteria criteria = DetachedCriteria.forClass(UserEntity.class);
        criteria.add(Restrictions.like("email", oldEmail));
        List<UserEntity> users = this.hibernateTemplate.findByCriteria(criteria);
        User userToUpdate = users.get(0);
        userToUpdate.setEmail(userNewParam.getEmail());
        userToUpdate.setPassword(userNewParam.getPassword());
        userToUpdate.setFirstName(userNewParam.getFirstName());
        userToUpdate.setLastName(userNewParam.getLastName());
        userToUpdate.setVk_link(userNewParam.getVk_link());
        userToUpdate.setUpdateDate(TimeManager.getTime());
        this.hibernateTemplate.update(userToUpdate);
    }

    @Override
    public void setBanned(String userEmail) {
        UserEntity user = (UserEntity) this.findEntityByEmail(userEmail);
        boolean banned = user.getIsBanned();
        user.setIsBanned(!banned);
        this.hibernateTemplate.update(user);
    }
}
