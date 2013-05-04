package it.sevenbits.dao;

import it.sevenbits.entity.User;

/**
 *Интерфейс, предоставляющий методы работы с сущностью User 
 */
public interface UserDao {
		void create(User user);
		User read(int id);
		void update(User user);
		void detele(User user);
}
