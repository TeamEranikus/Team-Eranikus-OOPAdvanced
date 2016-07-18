package escape.code.daos.puzzleRectangleDAO;

import escape.code.configurations.HibernateUtils;
import escape.code.models.PuzzleRectangle;
import javafx.scene.shape.Rectangle;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class PuzzleRectangleDaoImpl implements PuzzleRectangleDao{

    private static Session session;
    static {
        session = HibernateUtils.openSession();

    }
    @Override
    public void createPuzzleRectangle(PuzzleRectangle currentRectangle) {
        session.beginTransaction();
        session.persist(currentRectangle);
        session.getTransaction().commit();
    }

    @Override
    public PuzzleRectangle getFirst() {
        session.beginTransaction();
        Query query = session.createQuery("SELECT rect FROM PuzzleRectangle AS rect where rect.id=:id");
        query.setParameter("id",1L);
        List<PuzzleRectangle> rectangles = query.list();
        return rectangles.get(0);
    }

    @Override
    public List<PuzzleRectangle> getAllPuzzleRectangleByLevel(int level) {
        session.beginTransaction();
        Query query = session.createQuery("SELECT rect FROM PuzzleRectangle AS rect where rect.level like :level");
        query.setParameter("level",level);
        List<PuzzleRectangle> rectangles = query.list();
        return rectangles;
    }

    @Override
    public PuzzleRectangle getOneById(long id) {
        //session.beginTransaction();
        Query query = session.createQuery("SELECT rect FROM PuzzleRectangle AS rect where rect.id=:id");
        query.setParameter("id",id);
        List<PuzzleRectangle> rectangles = query.list();
        return rectangles.get(0);
    }
}
