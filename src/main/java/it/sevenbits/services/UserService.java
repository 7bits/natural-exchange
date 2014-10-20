package it.sevenbits.services;

import it.sevenbits.repository.dao.UserDao;
import it.sevenbits.repository.entity.User;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.web.controller.MainController;
import it.sevenbits.web.util.SortOrder;
import it.sevenbits.web.util.TimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public final int DEFAULT_USERS_PER_LIST = 8;
    public final String DEFAULT_USER_REGISTRATION_PASSWORD = "dsfklosdaaevvsdfywewehwehsdu";
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDao userDao;

    public User findById(final Long id) {
        return this.userDao.findById(id);
    }

    public User findEntityByVkId(final String id) {
        return this.userDao.findEntityByVkId(id);
    }

    public void changeBan(final Long id) {
        this.userDao.changeBan(id);
    }

    public List<User> getAllUsersExceptCurrent(String keyWords, Long dateFrom, Long dateTo, boolean isBanned, SortOrder currentSortOrder) {
        List<User> listUsers = this.userDao.findUsersByKeywordsDateAndBan(keyWords, dateFrom, dateTo, isBanned, currentSortOrder);
        User currentUser = authService.getUser();
        if (currentUser != null) {
            listUsers.remove(currentUser);
        }
        return listUsers;
    }

    public boolean checkRegistrationLink(final User user, final String code) {
        if (user == null) {
            return false;
        }
        if (TimeManager.getTime() > user.getActivationDate()) {
            return false;
        }
        if (!code.equals(user.getActivationCode())) {
            logger.info("check not passed: code not equals");
            return false;
        }
        return true;
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
}
