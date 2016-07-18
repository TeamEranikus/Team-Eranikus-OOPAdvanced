package escape.code.daos.userDAO;

import escape.code.configurations.HibernateUtils;
import escape.code.models.User;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private static Session session;
    static {
        session = HibernateUtils.openSession();
    }

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

    @Override
    public void create(User user) {
        User currentUser = this.getUserByName(user.getName());
        if(currentUser != null){
            throw new IllegalArgumentException("User already exist!");
        }
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }

    @Override
    public void updateUser(User user) {
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }

    private User getUserByName(String userName) {
        session.beginTransaction();
        Query query = session.createQuery("select us from User as us where us.name = :name");
        query.setParameter("name", userName);
        session.getTransaction().commit();
        List<User> users = query.list();
        if (users.size() == 0)
            return null;

        return users.get(0);
    }
}
