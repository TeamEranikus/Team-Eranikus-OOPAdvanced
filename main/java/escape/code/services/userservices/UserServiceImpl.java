package escape.code.services.userservices;

import escape.code.daos.userdaos.UserDao;
import escape.code.daos.userdaos.UserDaoImpl;
import escape.code.enums.Item;
import escape.code.models.User;

public class UserServiceImpl implements UserService {

    private static UserDao userDao;

    static {
        userDao = new UserDaoImpl();
    }

    @Override
    public void createUser(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setItem(Item.NONE);
        user.setLevel(0);
        this.userDao.create(user);

    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User getUser(String username, String password){
        User user = this.userDao.getLogedUser(username,password);
        return user;
    }
}
