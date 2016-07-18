package escape.code.services.userService;

import escape.code.daos.puzzleRectangleDAO.PuzzleRectangleDao;
import escape.code.daos.puzzleRectangleDAO.PuzzleRectangleDaoImpl;
import escape.code.daos.userDAO.UserDao;
import escape.code.daos.userDAO.UserDaoImpl;
import escape.code.enums.Item;
import escape.code.models.PuzzleRectangle;
import escape.code.models.User;
import escape.code.services.puzzleRectangleService.PuzzleRectangleService;
import escape.code.services.puzzleRectangleService.PuzzleRectangleServiceImpl;

public class UserServiceImpl implements UserService {

    private static UserDao userDao;
    private static PuzzleRectangleService puzzleRectangleService;

    static {
        userDao = new UserDaoImpl();
        puzzleRectangleService = new PuzzleRectangleServiceImpl();
    }

    @Override
    public void createUser(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setItem(Item.NONE);
        user.setLevel(0);
        PuzzleRectangle puzzleRectangle = puzzleRectangleService.getFirst();
        user.setPuzzleRectangle(puzzleRectangle);
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
