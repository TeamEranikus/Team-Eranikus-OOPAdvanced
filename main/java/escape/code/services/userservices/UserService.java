package escape.code.services.userservices;

import escape.code.models.User;

public interface UserService {
    void createUser(String username, String password);
    void updateUser(User user);
    User getUser(String username, String password);
}
