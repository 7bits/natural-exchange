package it.sevenbits.services;

import it.sevenbits.repository.dao.UserDao;
import it.sevenbits.repository.entity.User;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.web.util.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final int DEFAULT_USERS_PER_LIST = 8;

    @Autowired
    private UserDao userDao;

    public User findById(final Long id) {
        return this.userDao.findById(id);
    }

    public void changeBan(final Long id) {
        this.userDao.changeBan(id);
    }

    public List<User> getAllUsersExceptCurrent(String keyWords, Long dateFrom, Long dateTo, boolean isBanned, SortOrder currentSortOrder) {
        List<User> listUsers = this.userDao.findUsersByKeywordsDateAndBan(keyWords, dateFrom, dateTo, isBanned, currentSortOrder);
        User currentUser = AuthService.getUser();
        if (currentUser != null) {
            listUsers.remove(currentUser);
        }
        return listUsers;
    }

    public Boolean isExistUserWithEmail(final String email) {
        return this.userDao.isExistUserWithEmail(email);
    }

    public void createUser(final User user) {
        this.userDao.create(user);
    }

    public void updateData(final User user) {
        this.userDao.updateData(user);
    }

    public void updateActivationCode(final User user) {
        this.userDao.updateActivationCode(user);
    }

    public User findUserByEmail(final String email) {
        return this.userDao.findUserByEmail(email);
    }

    public int getDEFAULT_USERS_PER_LIST() {
        return DEFAULT_USERS_PER_LIST;
    }
}
