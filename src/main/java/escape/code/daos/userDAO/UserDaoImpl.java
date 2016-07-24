package escape.code.daos.userDAO;

import com.google.inject.Inject;
import escape.code.models.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserDaoImpl implements UserDao{

    @Inject
    private EntityManager entityManager;


    @Override
    public User getLogedUser(String username, String password) {
        User user = this.getUserByName(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found!");
        } else if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Password is incorrect!");
        }
        return user;
    }

    public void create(User user) {
        User currentUser = this.getUserByName(user.getName());
        if(currentUser != null){
            throw new IllegalArgumentException("User already exist!");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void updateUser(User user) {
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(user);
        this.entityManager.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    private User getUserByName(String userName) {
        Query query = this.entityManager.createQuery("select us from User as us where us.name = :name");
        query.setParameter("name", userName);
        List<User> users = query.getResultList();
        if (users.size() == 0){
            return null;
        }

        return users.get(0);
    }
}
