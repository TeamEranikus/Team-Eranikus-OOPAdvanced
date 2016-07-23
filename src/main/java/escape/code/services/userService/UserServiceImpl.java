package escape.code.services.userService;

import com.google.inject.Inject;
import escape.code.daos.userDAO.UserDao;
import escape.code.models.PuzzleRectangle;
import escape.code.models.User;
import escape.code.services.puzzleRectangleService.PuzzleRectangleService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private PuzzleRectangleService puzzleRectangleService;

    @Inject
    public UserServiceImpl(UserDao userDao, PuzzleRectangleService puzzleRectangleService) {
        this.userDao = userDao;
        this.puzzleRectangleService = puzzleRectangleService;
    }

    @Override
    public void createUser(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
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
