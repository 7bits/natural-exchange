package it.sevenbits.dao;

import it.sevenbits.entity.User;

import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью User 
 */
public interface UserDao {
    void create(User user);
    User findById(int id);
    List<User> find();
    void update(User user);
    void delete(User user);
}
