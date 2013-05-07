package it.sevenbits.dao.impl;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;

/**
 * Тестовая имплементация интерфейса UserDao
 */

public class UserDaoImpl implements UserDao {

	public void create(User user) {
	}

	public User findById(int id) {
		User user1 = new User();
		user1.setId(id);
		user1.setFirstName("Dmitry "+id);
		return user1;
	}

	public void update(User user) {
	}

	public void delete(User user) {
	}
	
	public String[] getAllUsers(){
		String[] users = new String[3];
		users[0] = "Dmitry1";
		users[1] = "Dmitry2";
		users[2] = "Dmitry3";
		return users;
	}

}
